package com.project.open_stall.dto.orderItems;

import com.project.open_stall.model.OrderItemSnapshot;
import lombok.Builder;

@Builder
public record OrderItemResponseDto(
        long id,
        OrderItemSnapshot snapshot,
        int quantity
) {
}
