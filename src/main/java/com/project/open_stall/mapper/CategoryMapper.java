package com.project.open_stall.mapper;

import com.project.open_stall.dto.categoryDto.*;
import com.project.open_stall.model.Category;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "numOfProducts",
            expression = "java(category.getProducts() != null ? category.getProducts().size() : 0)")
    CategoryResponseDto toResponse(Category category);

    List<CategoryResponseDto> toResponseList(List<Category> categories);

    CategoryDetailDto toDetail(Category category);

    Category toEntity(CategoryRequestDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(CategoryUpdateDto dto, @MappingTarget Category category);
}
