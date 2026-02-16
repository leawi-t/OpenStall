package com.project.open_stall.dto.productDto;

import com.project.open_stall.model.ProductImage;

import java.math.BigDecimal;
import java.util.List;

//for product details
public record ProductDetailDto(
        String name,
        String description,
        String model,
        int stockQuantity,
        BigDecimal salePrice,
        BigDecimal supplierCost,
        List<ProductImage> productImages,
        List<Long> categoryId,
        long supplierId,
        String supplierName
) {
}
