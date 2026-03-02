package com.project.open_stall.mapper;

import com.project.open_stall.dto.orderItems.OrderItemResponseDto;
import com.project.open_stall.model.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    OrderItemResponseDto toResponse(Order order);
}
