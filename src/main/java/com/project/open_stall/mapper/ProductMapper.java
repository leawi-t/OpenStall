package com.project.open_stall.mapper;

import com.project.open_stall.dto.productDto.ProductDetailDto;
import com.project.open_stall.dto.productDto.ProductRequestDto;
import com.project.open_stall.dto.productDto.ProductResponseDto;
import com.project.open_stall.dto.productDto.ProductUpdateDto;
import com.project.open_stall.model.Category;
import com.project.open_stall.model.Product;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {ProductImageMapper.class, CategoryMapper.class, SupplierProfileMapper.class})
public abstract class ProductMapper {

    @Autowired
    protected ProductImageMapper productImageMapper;

    @Mapping(target = "productImage",
            expression = "java(product.getProductImages() != null && !product.getProductImages().isEmpty() ? " +
                    "productImageMapper.toDto(product.getProductImages().get(0)) : null)")
    public abstract ProductResponseDto toResponse(Product product);

    public abstract ProductDetailDto toDetail(Product product);

    @Mapping(target = "categories", source = "categoryIds")
    public abstract Product toEntity(ProductRequestDto dto);

    Category mapIdToCategory(Long id) {
        if (id == null) return null;
        Category category = new Category();
        category.setId(id);
        return category;
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(
            source = "productImages",
            target = "productImages",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL
    )
    public abstract void updateEntity(ProductUpdateDto dto, @MappingTarget Product product);
}
