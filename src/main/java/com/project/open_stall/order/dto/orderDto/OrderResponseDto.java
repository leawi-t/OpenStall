package com.project.open_stall.order.dto.orderDto;

import com.project.open_stall.order.model.AddressSnapshot;

import java.math.BigDecimal;

public record OrderResponseDto(
        long id,
        String status,
        BigDecimal totalAmount,
        AddressSnapshot shippingAddress,
        int numberOfItems
) {
}
