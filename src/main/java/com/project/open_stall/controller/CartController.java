package com.project.open_stall.controller;
import com.project.open_stall.dto.cartDto.CartResponseDto;
import com.project.open_stall.dto.cartItemDto.CartItemRequestDto;
import com.project.open_stall.dto.cartItemDto.CartItemResponseDto;
import com.project.open_stall.dto.cartItemDto.CartItemUpdateDto;
import com.project.open_stall.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    // I added the {userId} because I haven't implemented security yet

    private final CartService service;

    @GetMapping("/{userId}")
    public ResponseEntity<CartResponseDto> getCart(@PathVariable long userId){
        return ResponseEntity.ok(service.getCart(userId));
    }

    @GetMapping("/{userId}/items")
    public ResponseEntity<List<CartItemResponseDto>> getCartItems(@PathVariable long userId){
        return ResponseEntity.ok(service.getCartItems(userId));
    }

    @GetMapping("/{userId}/items/{productId}")
    public ResponseEntity<CartItemResponseDto> getCartItemById(@PathVariable long userId,
                                                               @PathVariable long productId){
        return ResponseEntity.ok(service.getCartItem(userId, productId));
    }

    @PostMapping("/{userId}/items")
    public ResponseEntity<CartResponseDto> addItemToCart(@PathVariable long userId,
                                                         @RequestBody @Valid CartItemRequestDto dto){
        return new ResponseEntity<>(service.addItemToCart(userId, dto), HttpStatus.CREATED);
    }

    @PatchMapping("{userId}/items/{productId}")
    public ResponseEntity<CartResponseDto> updateCartItem(
            @PathVariable long userId,
            @PathVariable long productId,
            @RequestBody @Valid CartItemUpdateDto dto)
    {
        return ResponseEntity.ok(service.updateCartItem(userId, productId, dto));
    }

    @DeleteMapping("{userId}/items/{productId}")
    public ResponseEntity<CartResponseDto> removeCartItem(@PathVariable long userId, @PathVariable long productId){
        return ResponseEntity.ok(service.removeCartItem(userId, productId));
    }

    @DeleteMapping("{userId}/items")
    public ResponseEntity<CartResponseDto> clearCart(@PathVariable long userId){
        return ResponseEntity.ok(service.clearCart(userId));
    }
}
