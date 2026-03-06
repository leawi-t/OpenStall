package com.project.open_stall.category;

import com.project.open_stall.category.dto.CategoryDetailDto;
import com.project.open_stall.category.dto.CategoryRequestDto;
import com.project.open_stall.category.dto.CategoryResponseDto;
import com.project.open_stall.category.dto.CategoryUpdateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService service;

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getCategories(
            @RequestParam(required = false) String keyword) {
        if (keyword != null) {
            return ResponseEntity.ok(service.searchCategory(keyword));
        }
        return ResponseEntity.ok(service.getAllCategories());
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDetailDto> getCategoryById(@PathVariable long categoryId){
        return new ResponseEntity<>(service.getCategoryById(categoryId), HttpStatus.OK);
    }

    @PostMapping
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
