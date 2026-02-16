package com.project.open_stall.dto.categoryDto;

import java.util.List;

public record CategoryDetailDto(
        long id,
        String name,
        String description,
        List<Long> productId
) {
}

