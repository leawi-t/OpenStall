package com.project.open_stall.dto.cartItemDto;

import jakarta.validation.constraints.PositiveOrZero;

public record CartItemUpdateDto(
        @PositiveOrZero(message = "Quantity has to be at least 1")
        int quantity
) {
}
