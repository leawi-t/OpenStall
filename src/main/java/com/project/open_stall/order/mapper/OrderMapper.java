package com.project.open_stall.order.mapper;

import com.project.open_stall.order.dto.orderDto.OrderDetailsDto;
import com.project.open_stall.order.dto.orderDto.OrderResponseDto;
import com.project.open_stall.order.model.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = OrderItemMapper.class)
public interface OrderMapper {

    OrderResponseDto toResponse(Order order);

    OrderDetailsDto toDetails(Order order);
}
