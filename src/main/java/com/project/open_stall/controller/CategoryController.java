package com.project.open_stall.controller;

import com.project.open_stall.dto.categoryDto.CategoryDetailDto;
import com.project.open_stall.dto.categoryDto.CategoryRequestDto;
import com.project.open_stall.dto.categoryDto.CategoryResponseDto;
import com.project.open_stall.dto.categoryDto.CategoryUpdateDto;
import com.project.open_stall.dto.productDto.ProductResponseDto;
import com.project.open_stall.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    CategoryService service;

    @Autowired
    public CategoryController(CategoryService service){
        this.service = service;
    }

    @GetMapping
    public List<CategoryResponseDto> getAllProducts(){
        return service.getAllCategories();
    }

    @GetMapping("/{categoryId}")
    public CategoryDetailDto getProductById(@PathVariable long categoryId){
        return service.getCategoryById(categoryId);
    }

    @GetMapping("/search/{keyword}")
    public List<CategoryResponseDto> searchCategory(@PathVariable String keyword){
        return service.searchCategory(keyword);
    }

    @GetMapping("/{categoryId}/products")
    public Set<ProductResponseDto> getProductsByCategory(@PathVariable long categoryId){
        return service.getProductsByCategory(categoryId);
    }

    @PostMapping()
    public CategoryDetailDto addCategory(@RequestBody @Valid CategoryRequestDto dto){
        return service.addCategory(dto);
    }

    @PutMapping("/{categoryId}")
    public CategoryDetailDto updateCategory(
            @RequestBody @Valid CategoryUpdateDto dto,
            @PathVariable long categoryId)
    {
        return service.updateCategory(dto, categoryId);
    }


}
