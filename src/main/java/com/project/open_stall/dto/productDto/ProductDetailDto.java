package com.project.open_stall.dto.productDto;

import com.project.open_stall.dto.categoryDto.CategoryResponseDto;
import com.project.open_stall.dto.supplierProfileDto.SupplierProfileResponseDto;
import com.project.open_stall.model.ProductImage;

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
        List<ProductImage> productImages,
        List<CategoryResponseDto> categories,
        SupplierProfileResponseDto supplierProfile
) {
}
