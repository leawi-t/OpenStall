package com.project.open_stall.controller;

import com.project.open_stall.dto.categoryDto.*;
import com.project.open_stall.dto.productDto.ProductResponseDto;
import com.project.open_stall.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService service;

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories(){
        return new ResponseEntity<>(service.getAllCategories(), HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDetailDto> getCategoryById(@PathVariable long categoryId){
        return new ResponseEntity<>(service.getCategoryById(categoryId), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CategoryResponseDto>> searchCategory(@RequestParam String keyword){
        return new ResponseEntity<>(service.searchCategory(keyword), HttpStatus.OK);
    }

    @GetMapping("/{categoryId}/products")
    public ResponseEntity<Set<ProductResponseDto>> getProductsByCategory(@PathVariable long categoryId){
        return new ResponseEntity<>(service.getProductsByCategory(categoryId), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<CategoryDetailDto> addCategory(@RequestBody @Valid CategoryRequestDto dto){
        return new ResponseEntity<>(service.addCategory(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDetailDto> updateCategory(
            @RequestBody @Valid CategoryUpdateDto dto,
            @PathVariable long categoryId)
    {
        return new ResponseEntity<>(service.updateCategory(dto, categoryId), HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategoryById(@PathVariable long categoryId){
        service.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }

}
