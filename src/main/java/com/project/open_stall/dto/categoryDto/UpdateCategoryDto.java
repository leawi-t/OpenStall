package com.project.open_stall.dto.categoryDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateCategoryDto(
        @NotBlank
        @Size(max = 50, message = "Name can not be longer than 50 characters")
        String name,

        @NotBlank
        @Size(max = 1500, message = "Description can not be longer than 50 characters")
        String description
) {
}
