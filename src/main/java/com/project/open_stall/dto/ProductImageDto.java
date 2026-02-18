package com.project.open_stall.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProductImageDto(
        @NotBlank
        String url,

        @NotBlank
        @Size(max = 300)
        String altText,

        int orderOfDisplay
) {
}
