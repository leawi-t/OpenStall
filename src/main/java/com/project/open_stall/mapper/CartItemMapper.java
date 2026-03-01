package com.project.open_stall.mapper;

import com.project.open_stall.dto.cartItemDto.CartItemRequestDto;
import com.project.open_stall.dto.cartItemDto.CartItemResponseDto;
import com.project.open_stall.dto.cartItemDto.CartItemUpdateDto;
import com.project.open_stall.model.Cart;
import com.project.open_stall.model.CartItem;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public interface CartItemMapper {

    CartItemResponseDto toResponse(CartItem item);

    List<CartItemResponseDto> toResponseList(List<CartItem> items);

    CartItem toEntity(CartItemRequestDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(CartItemUpdateDto dto, @MappingTarget CartItem items);
}
