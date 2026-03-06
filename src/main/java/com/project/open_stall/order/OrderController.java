package com.project.open_stall.order;

import com.project.open_stall.order.dto.orderDto.OrderCancellationResponse;
import com.project.open_stall.order.dto.orderDto.OrderDetailsDto;
import com.project.open_stall.order.dto.orderDto.OrderResponseDto;
import com.project.open_stall.order.dto.orderItems.OrderItemResponseDto;
import com.project.open_stall.order.model.AddressSnapshot;
import com.project.open_stall.order.model.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    // TODO: fix the endpoints when security is added

    private final OrderService service;

    @GetMapping
    public ResponseEntity<PagedModel<OrderResponseDto>> getAllOrders(Pageable pageable) {
        Page<OrderResponseDto> page = service.getAllOrders(pageable);
        return ResponseEntity.ok(new PagedModel<>(page));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<PagedModel<OrderResponseDto>> getAllOrdersByUserId(@PathVariable long userId, Pageable pageable) {
        Page<OrderResponseDto> page = service.getAllOrdersByUser(userId, pageable);
        return ResponseEntity.ok(new PagedModel<>(page));
    }

    @GetMapping("/filter")
    public ResponseEntity<PagedModel<OrderResponseDto>> filter(
            @RequestParam(required = false) long userId,
            @RequestParam(required = false) BigDecimal min,
            @RequestParam(required = false) BigDecimal max,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) LocalDateTime start,
            @RequestParam(required = false)  LocalDateTime end,
            Pageable pageable
    ) {
        Page<OrderResponseDto> page = service.filter(pageable, userId, min, max, status, start, end);
        return ResponseEntity.ok(new PagedModel<>(page));
    }

    @GetMapping("/{userId}/{orderId}")
    public ResponseEntity<OrderDetailsDto> getOrderById(@PathVariable long userId, @PathVariable long orderId) {
        return ResponseEntity.ok(service.getOrderById(userId, orderId));
    }

    @GetMapping("/{userId}/{orderId}/{orderItemId}")
    public ResponseEntity<OrderItemResponseDto> getOrderItem(@PathVariable long userId,
                                                                 @PathVariable long orderId,
                                                                 @PathVariable long orderItemId) {
        return ResponseEntity.ok(service.getOrderItem(userId, orderId, orderItemId));
    }

    @PostMapping("/{userId}")
    public ResponseEntity<OrderDetailsDto> createOrder(@PathVariable long userId,
                                                       @RequestBody AddressSnapshot snapshot){
        return new ResponseEntity<>(service.createOrder(userId, snapshot), HttpStatus.CREATED);
    }

    @PatchMapping("/{userId}/{orderId}")
    public ResponseEntity<OrderCancellationResponse> cancelOrder(@PathVariable long userId, @PathVariable long orderId){
        return ResponseEntity.ok(service.cancelOrder(userId, orderId));
    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<OrderDetailsDto> updateStatus(
            @PathVariable Long orderId,
            @RequestParam OrderStatus newStatus) {

        return ResponseEntity.ok(service.updateOrderStatus(orderId, newStatus));
    }
}
