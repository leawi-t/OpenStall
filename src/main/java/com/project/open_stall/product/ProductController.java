package com.project.open_stall.product;

import com.project.open_stall.product.dto.*;
import com.project.open_stall.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    @GetMapping
    public ResponseEntity<PagedModel<ProductResponseDto>> getProducts(
            @RequestBody @Valid ProductFilterDto dto,
            Pageable pageable
    ){
        Page<ProductResponseDto> page = service.getProducts(dto, pageable);
        return ResponseEntity.ok(new PagedModel<>(page));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDetailDto> getProductById(@PathVariable long productId){
        return new ResponseEntity<>(service.getProductById(productId), HttpStatus.OK);
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<ProductDetailDto> addProduct(
            @RequestBody @Valid ProductRequestDto dto,
            @PathVariable long userId
    ){
        return new ResponseEntity<>(service.addProduct(dto, userId), HttpStatus.CREATED);
    }

    @PutMapping("/user/{userId}/{productId}")
    public ResponseEntity<ProductDetailDto> updateProduct(
            @RequestBody @Valid ProductUpdateDto dto,
            @PathVariable long userId,
            @PathVariable long productId
    ){
        return new ResponseEntity<>(service.updateProduct(dto, productId, userId), HttpStatus.CREATED);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProductById(@PathVariable long productId){
        service.deleteProductById(productId);
        return ResponseEntity.noContent().build();
    }
}
