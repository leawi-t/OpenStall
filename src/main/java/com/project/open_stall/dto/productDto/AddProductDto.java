package com.project.open_stall.dto.productDto;

import com.project.open_stall.model.ProductImage;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

//for user to add a product
public record AddProductDto(

        @NotBlank
        @Size(max = 100, message = "Product name can be at max 100 characters")
        String name,

        @NotBlank
        @Size(max = 2000, message = "Description can be at max 2000 characters")
        String description,

        @Size(max = 1000, message = "Model can be at max 1000 characters")
        String model,

        @NotNull
        @Min(value = 1, message = "Can't add a product with stock quantity less than 1")
        int stockQuantity,

        @NotNull
        @Min(value = 0, message = "Price can not be less than 0")
        BigDecimal salesPrice,

        @NotNull
        @Min(value = 0, message = "Price can not be less than 0")
        BigDecimal supplierCost,

        List<ProductImage> productImages,

        @Size(min = 1, max = 10)
        List<Long> categoryId,

        @Size(min = 1, max = 10)
        List<String> tags

) {
}
