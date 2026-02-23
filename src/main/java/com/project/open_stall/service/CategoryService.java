package com.project.open_stall.service;

import com.project.open_stall.dto.categoryDto.*;
import com.project.open_stall.dto.productDto.ProductResponseDto;
import com.project.open_stall.exception.ResourceNotFoundException;
import com.project.open_stall.mapper.CategoryMapper;
import com.project.open_stall.mapper.ProductMapper;
import com.project.open_stall.model.Category;
import com.project.open_stall.repo.CategoryRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepo categoryRepo;
    private final CategoryMapper categoryMapper;
    private final ProductMapper productMapper;

    public List<CategoryResponseDto> getAllCategories(){
        return categoryMapper.toResponseList(categoryRepo.findAll());
    }

    public CategoryDetailDto getCategoryById(long id){
        return categoryMapper.toDetail(categoryRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category with id: " + id + " was not found")));
    }

    public List<CategoryResponseDto> searchCategory(String keyword){
        return categoryMapper.toResponseList(categoryRepo.searchCategory(keyword));
    }

    public Set<ProductResponseDto> getProductsByCategory(long categoryId){
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category with id: " + categoryId + " was not found"));
        return productMapper.toResponseSet(category.getProducts());
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
