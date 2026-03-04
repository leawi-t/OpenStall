package com.project.open_stall.product.mapper;

import com.project.open_stall.product.dto.ProductImageDto;
import com.project.open_stall.product.model.ProductImage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductImageMapper {

    ProductImage toEntity(ProductImageDto dto);

    ProductImageDto toDto(ProductImage productImage);
}
