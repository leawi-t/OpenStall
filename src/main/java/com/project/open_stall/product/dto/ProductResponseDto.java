package com.project.open_stall.product.dto;

import java.math.BigDecimal;

//for basic product information
public record ProductResponseDto(
        long id,
        String name,
        String model,
        String description,
        int stockQuantity,
        BigDecimal salePrice,
        ProductImageDto productImage
) {
}
