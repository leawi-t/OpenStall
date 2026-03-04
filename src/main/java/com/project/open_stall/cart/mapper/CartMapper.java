package com.project.open_stall.cart.mapper;

import com.project.open_stall.cart.dto.cartDto.CartResponseDto;
import com.project.open_stall.cart.model.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = CartItemMapper.class)
public interface CartMapper {

    CartResponseDto toResponse(Cart cart);
}
