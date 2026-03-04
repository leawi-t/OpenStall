package com.project.open_stall.category.dto;

import com.project.open_stall.product.dto.ProductResponseDto;

import java.util.List;

public record CategoryDetailDto(
        long id,
        String name,
        String description,
        List<ProductResponseDto> products
) {
}

