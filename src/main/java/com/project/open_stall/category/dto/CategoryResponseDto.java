package com.project.open_stall.category.dto;

public record CategoryResponseDto(
        long id,
        String name,
        String description,
        int numOfProducts
) {
}
