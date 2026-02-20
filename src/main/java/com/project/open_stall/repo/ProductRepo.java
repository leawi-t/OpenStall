package com.project.open_stall.repo;

import com.project.open_stall.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    public List<Product> findByNameAndModelAndSalePriceGreaterThan(String name, String model, int salePrice);
}
