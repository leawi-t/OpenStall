package com.project.open_stall.mapper;

import com.project.open_stall.dto.productDto.ProductDetailDto;
import com.project.open_stall.dto.productDto.ProductRequestDto;
import com.project.open_stall.dto.productDto.ProductResponseDto;
import com.project.open_stall.dto.productDto.ProductUpdateDto;
import com.project.open_stall.model.Category;
import com.project.open_stall.model.Product;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductImageMapper.class, CategoryMapper.class, SupplierProfileMapper.class})
public interface ProductMapper {

    @Mapping(target = "primaryImage",
            expression = "java(product.getImages() != null && !product.getImages().isEmpty() ? product.getImages().get(0) : null)")
    ProductResponseDto toResponse(Product product);

    List<ProductResponseDto> toResponseList(List<Product> products);

    ProductDetailDto toDetail(Product product);

    @Mapping(target = "categories", source = "categoryIds")
    Product toEntity(ProductRequestDto dto);

    default Category mapIdToCategory(Long id) {
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
    void updateEntity(ProductUpdateDto dto, @MappingTarget Product product);
}
