package com.project.open_stall.repo;

import com.project.open_stall.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    Page<Product> findAll(Pageable pageable);

    // TODO: this might be better as JPASpecificationFilter
    public Page<Product> findByNameAndModelTrueAndSalePriceGreaterThan(String name, String model,
                                                                       BigDecimal salePrice, Pageable pageable);

    @Query(value = "SELECT p FROM product p", nativeQuery = true)
    public Page<Product> getAllProducts(Pageable pageable);
}
