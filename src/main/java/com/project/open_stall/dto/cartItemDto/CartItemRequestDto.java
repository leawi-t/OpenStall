package com.project.open_stall.dto.cartItemDto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CartItemRequestDto(
        @NotNull
        @Min(value=1 ,message = "Quantity has to be at least 1")
        int quantity,

        @NotNull
        long productId
) {
}
