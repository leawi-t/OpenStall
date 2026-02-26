package com.project.open_stall.repo;

import com.project.open_stall.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    @Query("SELECT DISTINCT p FROM product p JOIN p.categories c WHERE c.id = :categoryId")
    Page<Product> findByCategoryId(@Param("categoryId") long categoryId, Pageable pageable);

    Page<Product> findAll(Pageable pageable);
}
