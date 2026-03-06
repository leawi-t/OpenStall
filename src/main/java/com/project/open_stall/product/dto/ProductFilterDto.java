package com.project.open_stall.product.dto;

import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductFilterDto(
        Boolean active,

        @Size(max = 100, message = "Name can't be longer than 100 characters")
        String name,

        @Size(max = 100, message = "model can't be longer than 100 characters")
        String model,

        @PositiveOrZero
        BigDecimal min,

        @PositiveOrZero
        BigDecimal max,

        Long categoryId,

        Long supplierId,

        @Size(max = 1000, message = "description can't be longer than 1000 characters")
        String description,

        LocalDateTime start,

        LocalDateTime end
) {
}
