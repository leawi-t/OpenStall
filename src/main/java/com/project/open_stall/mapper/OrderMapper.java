package com.project.open_stall.mapper;

import com.project.open_stall.dto.orderDto.OrderDetailsDto;
import com.project.open_stall.dto.orderDto.OrderResponseDto;
import com.project.open_stall.model.Order;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = OrderItemMapper.class)
public interface OrderMapper {

    List<OrderResponseDto> toResponseList(List<Order> order);

    OrderDetailsDto toDetails(Order order);
}
