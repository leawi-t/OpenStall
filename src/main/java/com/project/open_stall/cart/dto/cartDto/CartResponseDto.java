package com.project.open_stall.cart.dto.cartDto;

import com.project.open_stall.cart.dto.CartItemDto.CartItemResponseDto;

import java.util.List;

public record CartResponseDto (
        long id,
        List<CartItemResponseDto> items
){
}
