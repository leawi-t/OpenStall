package com.project.open_stall.dto.productDto;

import com.project.open_stall.model.Category;
import com.project.open_stall.model.ProductImage;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

//to update the product
public record UpdateProductDto(
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
        @PositiveOrZero
        BigDecimal salesPrice,

        @NotNull
        @PositiveOrZero
        BigDecimal supplierCost,

        List<ProductImage> productImages,

        List<Category> categories,

        @Size(min = 1, max = 10)
        List<String> tags
) {
}
