package com.project.open_stall.service;

import com.project.open_stall.dto.cartDto.CartResponseDto;
import com.project.open_stall.dto.cartItemDto.CartItemRequestDto;
import com.project.open_stall.dto.cartItemDto.CartItemResponseDto;
import com.project.open_stall.dto.cartItemDto.CartItemUpdateDto;
import com.project.open_stall.exception.InsufficientStockException;
import com.project.open_stall.exception.ResourceNotFoundException;
import com.project.open_stall.mapper.CartItemMapper;
import com.project.open_stall.mapper.CartMapper;
import com.project.open_stall.model.Cart;
import com.project.open_stall.model.CartItem;
import com.project.open_stall.model.Product;
import com.project.open_stall.repo.CartRepo;
import com.project.open_stall.repo.ProductRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepo repo;
    private final CartMapper mapper;
    private final ProductRepo productRepo;
    private final CartItemMapper itemMapper;

    public Cart getCartEntity(long userId){
        return repo.findById(userId).
                orElseThrow((()->new ResourceNotFoundException("Cart with id: " + userId + " was not found")));
    }

    public CartResponseDto getCart(long userId){
        return mapper.toResponse(getCartEntity(userId));
    }

    public List<CartItemResponseDto> getCartItems(long userId){
        Cart cart = getCartEntity(userId);

        return itemMapper.toResponseList(cart.getItems());
    }

    public CartItemResponseDto getCartItem(long userId, long productId){
        Cart cart = getCartEntity(userId);
        CartItem item = cart.getItems().stream()
                .filter(x-> x.getProduct().getId() == productId).findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Cart item was not found"));
        return itemMapper.toResponse(item);
    }

    @Transactional
    public CartResponseDto addItemToCart(long userId, CartItemRequestDto dto) {
        Cart cart = getCartEntity(userId);

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId() == dto.productId())
                .findFirst();

        Product product = productRepo.findById(dto.productId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        if (existingItem.isPresent()) {
            if (product.getStockQuantity() < existingItem.get().getQuantity() + dto.quantity()){
                throw new InsufficientStockException("Product: " + product.getId() + " has Insufficient stock. Available: " + product.getStockQuantity() + " requested: " + existingItem.get().getQuantity() + dto.quantity());
            }
            existingItem.get().setQuantity(existingItem.get().getQuantity() + dto.quantity());
        } else {
            if (product.getStockQuantity() < dto.quantity()){
                throw new InsufficientStockException("Product: " + product.getId() + " has Insufficient stock. Available: " + product.getStockQuantity() + " requested: " + dto.quantity());
            }

            CartItem newItem = new CartItem();
            newItem.setProduct(product);
            newItem.setQuantity(dto.quantity());
            cart.addItem(newItem);
        }

        return mapper.toResponse(repo.save(cart));
    }

    @Transactional
    public CartResponseDto updateCartItem(long userId, long productId, CartItemUpdateDto dto){
        Cart cart = getCartEntity(userId);
        CartItem item = cart.getItems().stream()
                .filter(x->x.getProduct().getId() == productId)
                .findFirst()
                .orElseThrow(()-> new ResourceNotFoundException("The item does not exist"));

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        if (product.getStockQuantity() < dto.quantity())
            throw new InsufficientStockException("Product: " + product.getId() + " has Insufficient stock. Available: " + product.getStockQuantity() + " requested: " + dto.quantity());

        item.setQuantity(dto.quantity());
        return mapper.toResponse(cart);
    }

    @Transactional
    public CartResponseDto removeCartItem(long userId, long productId){
        Cart cart = getCartEntity(userId);

        cart.getItems().removeIf(x -> x.getProduct().getId() == productId);
        return mapper.toResponse(cart);
    }

    @Transactional
    public CartResponseDto clearCart(long userId){
        Cart cart = getCartEntity(userId);
        cart.getItems().clear();
        return mapper.toResponse(cart);
    }
}
