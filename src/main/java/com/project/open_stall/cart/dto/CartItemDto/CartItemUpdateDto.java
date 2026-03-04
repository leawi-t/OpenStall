package com.project.open_stall.cart.dto.CartItemDto;

import jakarta.validation.constraints.PositiveOrZero;

public record CartItemUpdateDto(
        @PositiveOrZero(message = "Quantity has to be at least 1")
        int quantity
) {
}
