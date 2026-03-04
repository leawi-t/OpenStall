package com.project.open_stall.cart.mapper;

import com.project.open_stall.cart.model.CartItem;
import com.project.open_stall.product.mapper.ProductMapper;
import com.project.open_stall.cart.dto.CartItemDto.CartItemResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public interface CartItemMapper {

    CartItemResponseDto toResponse(CartItem item);

    List<CartItemResponseDto> toResponseList(List<CartItem> items);
}
