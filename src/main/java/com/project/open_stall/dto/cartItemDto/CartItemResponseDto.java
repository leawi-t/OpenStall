package com.project.open_stall.dto.cartItemDto;

import com.project.open_stall.dto.productDto.ProductResponseDto;

public record CartItemResponseDto(
        long id,
        int quantity,
        ProductResponseDto dto
) {
}
