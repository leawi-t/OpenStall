package com.project.open_stall.order.dto.orderDto;

import com.project.open_stall.order.dto.orderItems.OrderItemResponseDto;
import com.project.open_stall.order.model.AddressSnapshot;

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
