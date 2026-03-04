package com.project.open_stall.order;

import com.project.open_stall.cart.model.Cart;
import com.project.open_stall.cart.model.CartItem;
import com.project.open_stall.cart.CartService;
import com.project.open_stall.common.exception.InsufficientStockException;
import com.project.open_stall.common.exception.InvalidOperationException;
import com.project.open_stall.common.exception.ResourceNotFoundException;
import com.project.open_stall.order.dto.orderDto.OrderCancellationResponse;
import com.project.open_stall.order.dto.orderDto.OrderDetailsDto;
import com.project.open_stall.order.dto.orderDto.OrderResponseDto;
import com.project.open_stall.order.model.*;
import com.project.open_stall.product.model.Product;
import com.project.open_stall.order.dto.orderItems.OrderItemResponseDto;
import com.project.open_stall.order.mapper.OrderItemMapper;
import com.project.open_stall.order.mapper.OrderMapper;
import com.project.open_stall.user.UserRepo;
import com.project.open_stall.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepo orderRepo;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final UserRepo userRepo;
    private final CartService cartService;

    @PreAuthorize("hasRole(Admin)")
    public Page<OrderResponseDto> getAllOrders(Pageable pageable){
        return orderRepo.findAll(pageable).map(orderMapper::toResponse);
    }

    public Page<OrderResponseDto> getAllOrdersByUser(long userId, Pageable pageable) {
        Specification<Order> spec = Specification.where(OrderSpecs.hasUserId(userId));
        return orderRepo.findAll(spec, pageable).map(orderMapper::toResponse);
    }

    @PreAuthorize("hasRole(Admin)")
    public Page<OrderResponseDto> filter(Pageable pageable, long userId, BigDecimal min,
                                         BigDecimal max, String status, LocalDateTime start, LocalDateTime end){

        OrderStatus orderStatus = (status != null) ? OrderStatus.valueOf(status.toUpperCase()) : null;

        Specification<Order> spec = Specification.where(OrderSpecs.hasUserId(userId))
                .and(OrderSpecs.hasStatus(orderStatus))
                .and(OrderSpecs.createdBetween(start, end))
                .and(OrderSpecs.hasTotalAmountBetween(min, max));

        return  orderRepo.findAll(spec, pageable).map(orderMapper::toResponse);
    }

    public OrderDetailsDto getOrderById(long userId, long orderId){
        return orderMapper.toDetails(orderRepo.findByIdAndUserId(orderId, userId)
                .orElseThrow(()-> new ResourceNotFoundException("Order with id: " + orderId + " was not found")));
    }

    public OrderItemResponseDto getOrderItem(long userId, long orderId, long orderItemId){
        Order order = orderRepo.findByIdAndUserId(userId, orderId)
                .orElseThrow(()-> new ResourceNotFoundException("Order was not found"));

        OrderItem item = order.getOrderItems().stream().
                filter(x->x.getId() == orderItemId)
                .findFirst()
                .orElseThrow(()-> new ResourceNotFoundException( "The order item was not found"));

        return orderItemMapper.toResponse(item);
    }

    @Transactional
    public OrderDetailsDto createOrder(long userId, AddressSnapshot snapshot) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Cart cart = user.getCart();
        if (cart.getItems().isEmpty()) {
            throw new InvalidOperationException("Cannot place an order with an empty cart");
        }

        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        order.setShippingAddress(snapshot);

        List<OrderItem> orderItems = cart.getItems().stream()
                .map(cartItem -> {
                    OrderItem orderItem = mapCartItemToOrderItem(cartItem);
                    orderItem.setOrder(order);
                    return orderItem;
                }).toList();

        BigDecimal total = orderItems.stream()
                .map(item -> item.getSnapshot().getPriceAtPurchase()
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setOrderItems(orderItems);
        order.setTotalAmount(total);

        Order savedOrder = orderRepo.save(order);
        cartService.clearCart(userId);

        return orderMapper.toDetails(savedOrder);
    }

    public OrderItem mapCartItemToOrderItem(CartItem cartItem){
        OrderItem item = new OrderItem();

        Product product = cartItem.getProduct();
        OrderItemSnapshot snapshot = new OrderItemSnapshot();

        if (product.getStockQuantity() < cartItem.getQuantity()){
            throw new InsufficientStockException("Available: " + product.getStockQuantity() + " requested: " + cartItem.getQuantity());
        }

        snapshot.setPriceAtPurchase(product.getSalePrice());
        snapshot.setSupplierCostAtPurchase(product.getSupplierCost());
        snapshot.setProductNameAtPurchase(product.getName());

        item.setProduct(product);
        item.setSnapshot(snapshot);
        item.setQuantity(cartItem.getQuantity());

        product.setStockQuantity(product.getStockQuantity()-cartItem.getQuantity());

        return item;
    }

    @Transactional
    public OrderCancellationResponse cancelOrder(long userId, long orderId) {
        Order order = orderRepo.findByIdAndUserId(orderId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        //I was not sure about this
        if (order.getStatus() == OrderStatus.SHIPPED || order.getStatus() == OrderStatus.DELIVERED) {
            throw new InvalidOperationException("Cannot cancel an order that is already paid or shipped");
        }

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new InvalidOperationException("Order is already cancelled");
        }

        order.setStatus(OrderStatus.CANCELLED);

        for (OrderItem item : order.getOrderItems()) {
            Product product = item.getProduct();
            product.setStockQuantity(product.getStockQuantity() + item.getQuantity());
        }

        orderRepo.save(order);

        return OrderCancellationResponse.builder()
                .orderId(orderId)
                .status(String.valueOf(OrderStatus.CANCELLED))
                .message("Order cancelled successfully. Your refund is being processed.")
                .cancelledAt(LocalDateTime.now())
                .build();
    }

    @Transactional
    public OrderDetailsDto updateOrderStatus(long orderId, OrderStatus newStatus) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (!order.getStatus().canTransitionTo(newStatus)) {
            throw new InvalidOperationException(
                    "Cannot move order from " + order.getStatus() + " to " + newStatus
            );
        }

        order.setStatus(newStatus);
        return orderMapper.toDetails(orderRepo.save(order));
    }
}
