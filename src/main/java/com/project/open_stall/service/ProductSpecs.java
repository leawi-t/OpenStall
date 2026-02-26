package com.project.open_stall.service;

import com.project.open_stall.model.Product;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProductSpecs {
    public static Specification<Product> isActive(){
        return ((root, query, cb) -> cb.equal(root.get("active"), true));
    }

    public static Specification<Product> hasName(String name){
        return ((root, query, cb) -> {
            if (name==null || name.isEmpty()) return null;
            return cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        });
    }

    public static Specification<Product> hasModel(String model){
        return ((root, query, cb) -> {
            if (model == null || model.isEmpty()) return null;
            return cb.like(cb.lower(root.get("model")), '%' + model.toLowerCase() + '%');
        });
    }

    public static Specification<Product> hasDescription(String description){
        return ((root, query, cb) -> {
            if (description == null || description.isEmpty()) return null;
            return cb.like(cb.lower(root.get("model")), '%' + description.toLowerCase() + '%');
        });
    }

    public static Specification<Product> maxPrice(BigDecimal salePrice){
        return (root, query, cb) -> {
            if (salePrice == null || salePrice.compareTo(BigDecimal.ZERO) < 0) return null;
            return cb.lessThanOrEqualTo(root.get("salePrice"), salePrice);
        };
    }

    public static Specification<Product> hasCategory(Long categoryId) {
        return (root, query, cb) -> {
            if (categoryId == null) return null;
            return cb.equal(root.join("categories").get("id"), categoryId);
        };
    }
}
