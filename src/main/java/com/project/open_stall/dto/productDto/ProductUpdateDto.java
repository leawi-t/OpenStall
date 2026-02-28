package com.project.open_stall.dto.productDto;

import com.project.open_stall.model.ProductImage;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

public record ProductUpdateDto(

        @Size(max = 100, min = 1, message = "Product name can be at max 100 characters and minimum 1 character")
        String name,

        @Size(max = 2000, message = "Description can be at max 2000 characters")
        String description,

        @Size(max = 1000, message = "Model can be at max 1000 characters")
        String model,

        @Min(value = 1, message = "Can't add a product with stock quantity less than 1")
        int stockQuantity,

        @Min(value = 0, message = "Price can not be less than 0")
        BigDecimal salePrice,

        @Min(value = 0, message = "Price can not be less than 0")
        BigDecimal supplierCost,

        @Valid
        List<ProductImage> productImages,

        @Size(min = 1, max = 10)
        List<Long> categoryIds,

        @Size(min = 1, max = 10)
        List<String> tags
){
}
