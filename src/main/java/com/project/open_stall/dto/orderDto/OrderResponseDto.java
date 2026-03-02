package com.project.open_stall.dto.orderDto;

import com.project.open_stall.model.AddressSnapshot;

import java.math.BigDecimal;

public record OrderResponseDto(
        long id,
        String status,
        BigDecimal totalAmount,
        AddressSnapshot shippingAddress,
        int numberOfItems
) {
}
