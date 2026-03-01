package com.project.open_stall.mapper;

import com.project.open_stall.dto.cartDto.CartResponseDto;
import com.project.open_stall.model.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = CartItemMapper.class)
public interface CartMapper {

    CartResponseDto toResponse(Cart cart);
}
