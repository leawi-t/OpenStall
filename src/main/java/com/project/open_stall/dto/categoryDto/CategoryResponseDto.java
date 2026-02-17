package com.project.open_stall.dto.categoryDto;

public record CategoryResponseDto(
        long id,
        String name,
        String description,
        int numOfProducts
) {
}
