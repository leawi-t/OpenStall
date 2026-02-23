package com.project.open_stall.controller;

import com.project.open_stall.dto.productDto.*;
import com.project.open_stall.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

//TODO: Instead of deleting set boolean active = false

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    @Autowired
    public ProductController(ProductService service){
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts(){
        return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDetailDto> getProductById(@PathVariable long productId){
        return new ResponseEntity<>(service.getProductById(productId), HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<ProductResponseDto>> filterProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) BigDecimal salePrice
    ){
        return new ResponseEntity<>(service.filter(name, model, salePrice), HttpStatus.OK);
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
