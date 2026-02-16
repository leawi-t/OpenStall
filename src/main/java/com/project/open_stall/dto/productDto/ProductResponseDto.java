package com.project.open_stall.dto.productDto;

import com.project.open_stall.model.ProductImage;

import java.math.BigDecimal;

//for basic product information
public record ProductResponseDto(
        String name,
        String model,
        int stockQuantity,
        BigDecimal salePrice,
        ProductImage productImage,
        long supplierId,
        long categoryId
) {
}
