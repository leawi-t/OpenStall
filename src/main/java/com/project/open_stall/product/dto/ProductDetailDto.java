package com.project.open_stall.product.dto;

import com.project.open_stall.category.dto.CategoryResponseDto;
import com.project.open_stall.supplierProfile.dto.SupplierProfileResponseDto;

import java.math.BigDecimal;
import java.util.List;

//for product details
public record ProductDetailDto(
        long id,
        String name,
        String description,
        String model,
        int stockQuantity,
        BigDecimal salePrice,
        List<ProductImageDto> productImages,
        List<CategoryResponseDto> categories,
        List<String> tags,
        SupplierProfileResponseDto supplierProfile
) {
}
