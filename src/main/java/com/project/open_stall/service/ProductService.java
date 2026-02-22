package com.project.open_stall.service;

import com.project.open_stall.dto.productDto.*;
import com.project.open_stall.exception.*;
import com.project.open_stall.mapper.*;
import com.project.open_stall.model.Category;
import com.project.open_stall.model.Product;
import com.project.open_stall.model.SupplierProfile;
import com.project.open_stall.model.User;
import com.project.open_stall.repo.CategoryRepo;
import com.project.open_stall.repo.ProductRepo;
import com.project.open_stall.repo.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.nio.file.AccessDeniedException;
import java.util.HashSet;
import java.util.List;

// TODO: make a filter method with different attributes with JPASpecificationFilter
// TODO: Add add product and update product in the supplierProfile service

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepo productRepo;
    private final ProductMapper productMapper;
    private final CategoryRepo categoryRepo;
    private final CategoryMapper categoryMapper;
    private final UserRepo userRepo;

    public List<ProductResponseDto> getAllProducts(){
        return productMapper.toResponseList(productRepo.findAll());
    }

    public ProductDetailDto getProductById(long productId){
        return productMapper.toDetail(productRepo.findById(productId).orElseThrow(
                ()->new ResourceNotFoundException("The product with id: " + productId + " was not found")));
    }

    public List<ProductResponseDto> filter(String name, String model, BigDecimal salePrice) {
        return productMapper.toResponseList(productRepo.findByNameAndModelAndSalePriceGreaterThan(name, model, salePrice));
    }

    @Transactional
    @PreAuthorize("hasRole('SUPPLIER')")
    public ProductDetailDto addProduct(ProductRequestDto dto, long userId){
        Product product = productMapper.toEntity(dto);

        List<Category> categories = categoryRepo.findAllById(dto.categoryIds());
        product.setCategories(new HashSet<>(categories));

        User user = userRepo.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User with " + userId + " does not exist"));

        product.setSupplierProfile(user.getSupplierProfile());
        return productMapper.toDetail(productRepo.save(product));
    }

    @Transactional
    public ProductDetailDto updateProduct(ProductUpdateDto dto, long productId, long userId) {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        if (product.getSupplierProfile().getUser().getId() != userId) {
            throw new InvalidOperationException("Not Authorized to update the product");
        }

        productMapper.updateEntity(dto, product);

        List<Category> categories = categoryRepo.findAllById(dto.categoryId());
        product.getCategories().clear();

        for (Category x: categories) product.getCategories().add(x);

        return productMapper.toDetail(productRepo.save(product));
    }

    public void deleteProductById(long productId){
        if (!productRepo.existsById(productId)){
            throw new ResourceNotFoundException("The product with id: " + productId + " was not found");
        }
        productRepo.deleteById(productId);
    }
}
