package com.project.open_stall.dto.productDto;

import com.project.open_stall.dto.ProductImageDto;

import java.math.BigDecimal;

//for basic product information
public record ProductResponseDto(
        long id,
        String name,
        String model,
        int stockQuantity,
        BigDecimal salePrice,
        ProductImageDto productImage
) {
}
