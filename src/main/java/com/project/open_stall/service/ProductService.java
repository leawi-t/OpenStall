package com.project.open_stall.service;

import com.project.open_stall.dto.productDto.ProductDetailDto;
import com.project.open_stall.dto.productDto.ProductResponseDto;
import com.project.open_stall.exception.ResourceNotFoundException;
import com.project.open_stall.mapper.CategoryMapper;
import com.project.open_stall.mapper.ProductMapper;
import com.project.open_stall.repo.CategoryRepo;
import com.project.open_stall.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// TODO: make a filter method with different attributes with JPASpecificationFilter
// TODO: Add add product and update product in the supplierProfile service

@Service
public class ProductService {

    private final ProductRepo productRepo;
    private final ProductMapper productMapper;
    private final CategoryRepo categoryRepo;
    private final CategoryMapper categoryMapper;

    @Autowired
    public ProductService(ProductRepo productRepo, ProductMapper productMapper,
                          CategoryRepo categoryRepo, CategoryMapper categoryMapper) {
        this.productRepo = productRepo;
        this.productMapper = productMapper;
        this.categoryRepo = categoryRepo;
        this.categoryMapper = categoryMapper;
    }

    public List<ProductResponseDto> getAllProducts(){
        return productMapper.toResponseList(productRepo.findAll());
    }

    public ProductDetailDto getProductById(long productId){
        return productMapper.toDetail(productRepo.findById(productId).orElseThrow(
                ()->new ResourceNotFoundException("The product with id: " + productId + " was not found")));
    }

    public List<ProductResponseDto> filter(String name, String model, int salePrice) {
        return productMapper.toResponseList(productRepo.findByNameAndModelAndSalePriceGreaterThan(name, model, salePrice));
    }

    public void deleteProductById(long productId){
        if (!productRepo.existsById(productId)){
            throw new ResourceNotFoundException("The product with id: " + productId + " was not found");
        }
        productRepo.deleteById(productId);
    }
}
