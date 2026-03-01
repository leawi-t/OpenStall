package com.project.open_stall.dto.cartDto;

import com.project.open_stall.dto.cartItemDto.CartItemResponseDto;

import java.util.List;

public record CartResponseDto (
        long id,
        List<CartItemResponseDto> items
){
}
