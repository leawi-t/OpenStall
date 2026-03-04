package com.project.open_stall.order.dto.orderItems;

import com.project.open_stall.order.model.OrderItemSnapshot;
import lombok.Builder;

@Builder
public record OrderItemResponseDto(
        long id,
        OrderItemSnapshot snapshot,
        int quantity
) {
}
