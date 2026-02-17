package com.project.open_stall.dto.categoryDto;

import jakarta.validation.constraints.Size;

public record CategoryUpdateDto(

        @Size(max = 50, message = "Name can not be longer than 50 characters")
        String name,

        @Size(max = 1500, message = "Description can not be longer than 50 characters")
        String description
) {
}
