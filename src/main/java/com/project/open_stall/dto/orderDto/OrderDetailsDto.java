package com.project.open_stall.dto.orderDto;

import com.project.open_stall.dto.orderItems.OrderItemResponseDto;
import com.project.open_stall.model.AddressSnapshot;

import java.math.BigDecimal;
import java.util.List;

public record OrderDetailsDto(
        long id,
        String status,
        BigDecimal totalAmount,
        AddressSnapshot shippingAddress,
        List<OrderItemResponseDto> orderItems
) {
}
