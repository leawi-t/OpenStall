package com.project.open_stall.product.service;

import com.project.open_stall.product.model.Product;
import org.springframework.beans.factory.BeanRegistry;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductSpecs {
    public static Specification<Product> isActive(Boolean active){
        return (root, query, cb) -> {
            if (active == null) return null;
            return cb.equal(root.get("active"), active);
        };
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
            return cb.like(cb.lower(root.get("description")), '%' + description.toLowerCase() + '%');
        });
    }

    public static Specification<Product> hasPrice(BigDecimal min, BigDecimal max){
        return (root, query, cb) -> {
            if (min == null && max == null) return null;
            if (min == null) return cb.lessThanOrEqualTo(root.get("salePrice"), max);
            if (max == null) return cb.greaterThanOrEqualTo(root.get("salePrice"), min);
            return cb.between(root.get("salePrice"), min ,max);
        };
    }

    public static Specification<Product> hasCategory(Long categoryId) {
        return (root, query, cb) -> {
            if (categoryId == null) return null;
            return cb.equal(root.join("categories").get("id"), categoryId);
        };
    }

    public static Specification<Product> hasSupplier(Long supplierId) {
        return (root, query, cb) -> {
            if (supplierId == null) return null;
            return cb.equal(root.join("supplierProfile").get("id"), supplierId);
        };
    }

    public static Specification<Product> hasDate(LocalDateTime min, LocalDateTime max){
        return (root, query, cb) -> {
            if (min == null && max == null) return null;
            if (min == null) return cb.lessThanOrEqualTo(root.get("createdAt"), max);
            if (max == null) return cb.greaterThanOrEqualTo(root.get("createdAt"), min);
            return cb.between(root.get("createdAt"), min ,max);
        };
    }
}
