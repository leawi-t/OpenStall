package com.project.open_stall.service;

import com.project.open_stall.dto.productDto.*;
import com.project.open_stall.exception.*;
import com.project.open_stall.mapper.*;
import com.project.open_stall.model.Category;
import com.project.open_stall.model.Product;
import com.project.open_stall.model.User;
import com.project.open_stall.repo.CategoryRepo;
import com.project.open_stall.repo.ProductRepo;
import com.project.open_stall.repo.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepo productRepo;
    private final ProductMapper productMapper;
    private final CategoryRepo categoryRepo;
    private final UserRepo userRepo;

    @PreAuthorize("hasRole('ADMIN')")
    public Page<ProductResponseDto> getAllProducts(Pageable pageable) {
        Page<Product> products = productRepo.findAll(pageable);
        return products.map(productMapper::toResponse);
    }

    public Page<ProductResponseDto> getAllActiveProducts(Pageable pageable){
        Specification<Product> spec = Specification.where(ProductSpecs.isActive());
        return productRepo.findAll(spec, pageable).map(productMapper::toResponse);
    }

    public ProductDetailDto getProductById(long productId){
        Product product = productRepo.findById(productId).
                orElseThrow(()->new ResourceNotFoundException("Product with id: " + productId + " was not found"));

        if (!product.isActive()) throw new ResourceNotFoundException("Product with id: " + productId + " was not found");
        return productMapper.toDetail(product);
    }

    public Page<ProductResponseDto> filter(String name, String model, BigDecimal salePrice,
                                           Long categoryId, String description, Pageable pageable) {
        Specification<Product> spec = Specification.where(ProductSpecs.isActive())
                .and(ProductSpecs.hasName(name))
                .and(ProductSpecs.hasModel(model))
                .and(ProductSpecs.maxPrice(salePrice))
                .and(ProductSpecs.hasCategory(categoryId))
                .and(ProductSpecs.hasDescription(description));
        return productRepo.findAll(spec, pageable).map(productMapper::toResponse);
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
    @PreAuthorize("hasRole('SUPPLIER')")
    public ProductDetailDto updateProduct(ProductUpdateDto dto, long productId, long userId) {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        if (product.getSupplierProfile().getUser().getId() != userId) {
            throw new InvalidOperationException("Not Authorized to update the product");
        }

        if (!product.isActive()) throw new ResourceNotFoundException("The Product does not exist");

        productMapper.updateEntity(dto, product);

        List<Category> categories = categoryRepo.findAllById(dto.categoryIds());
        product.getCategories().clear();

        product.setCategories(new HashSet<>(categories));

        return productMapper.toDetail(productRepo.save(product));
    }

    @Transactional
    public void deleteProductById(long productId){
        if (!productRepo.existsById(productId)){
            throw new ResourceNotFoundException("The product with id: " + productId + " was not found");
        }
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        product.setActive(false);
    }
}
