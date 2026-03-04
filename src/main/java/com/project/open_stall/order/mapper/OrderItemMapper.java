package com.project.open_stall.order.mapper;

import com.project.open_stall.order.dto.orderItems.OrderItemResponseDto;
import com.project.open_stall.order.model.OrderItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    OrderItemResponseDto toResponse(OrderItem order);
}
