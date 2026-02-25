package com.project.open_stall.service;

import com.project.open_stall.dto.categoryDto.*;
import com.project.open_stall.dto.productDto.ProductResponseDto;
import com.project.open_stall.exception.ResourceNotFoundException;
import com.project.open_stall.mapper.CategoryMapper;
import com.project.open_stall.mapper.ProductMapper;
import com.project.open_stall.model.Category;
import com.project.open_stall.model.Product;
import com.project.open_stall.repo.CategoryRepo;
import com.project.open_stall.repo.ProductRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepo categoryRepo;
    private final CategoryMapper categoryMapper;
    private final ProductMapper productMapper;
    private final ProductRepo productRepo;

    public List<CategoryResponseDto> getAllCategories(){
        return categoryMapper.toResponseList(categoryRepo.findAll());
    }

    public CategoryDetailDto getCategoryById(long id){
        return categoryMapper.toDetail(categoryRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category with id: " + id + " was not found")));
    }

    public List<CategoryResponseDto> searchCategory(String keyword){
        return categoryMapper.toResponseList(categoryRepo.searchCategory(keyword));
    }

    public Page<ProductResponseDto> getProductsByCategory(long categoryId, Pageable pageable){
        Category category = categoryRepo.findById(categoryId).
                orElseThrow(() -> new ResourceNotFoundException("Category with id: " + categoryId + " was not found"));
        Page<Product> products = productRepo.findByCategoryId(categoryId, pageable);
        return products.map(productMapper::toResponse);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public CategoryDetailDto addCategory(CategoryRequestDto dto){
        return categoryMapper.toDetail(categoryRepo.save(categoryMapper.toEntity(dto)));
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public CategoryDetailDto updateCategory(CategoryUpdateDto dto, long categoryId){
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category with id: " + categoryId + " was not found"));
        categoryMapper.updateEntity(dto, category);
        return categoryMapper.toDetail(categoryRepo.save(category));
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCategory(long categoryId){
        if (!categoryRepo.existsById(categoryId)){
            throw new ResourceNotFoundException("Category with id: " + categoryId + " was not found");
        }
        categoryRepo.deleteById(categoryId);
    }

}
