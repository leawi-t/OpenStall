package com.project.open_stall.mapper;

import com.project.open_stall.dto.ProductImageDto;
import com.project.open_stall.model.ProductImage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductImageMapper {

    ProductImage toEntity(ProductImageDto dto);
}
