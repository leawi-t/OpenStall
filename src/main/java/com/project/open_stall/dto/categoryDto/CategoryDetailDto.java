package com.project.open_stall.dto.categoryDto;

import com.project.open_stall.dto.productDto.ProductResponseDto;

import java.util.List;

public record CategoryDetailDto(
        long id,
        String name,
        String description,
        List<ProductResponseDto> products
) {
}

