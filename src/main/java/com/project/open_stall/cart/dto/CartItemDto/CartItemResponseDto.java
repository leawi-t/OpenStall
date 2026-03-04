package com.project.open_stall.cart.dto.CartItemDto;

import com.project.open_stall.product.dto.ProductResponseDto;

public record CartItemResponseDto(
        long id,
        int quantity,
        ProductResponseDto dto
) {
}
