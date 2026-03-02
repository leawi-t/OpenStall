package com.project.open_stall.dto.orderDto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record OrderCancellationResponse(
        long orderId,
        String status,
        LocalDateTime cancelledAt,
        String message


) {
}
