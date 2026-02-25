package com.project.open_stall.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "product")
@SQLRestriction("deleted = false")
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Product name can not be blank")
    @Size(max = 255)
    private String name;

    @NotBlank(message = "Description of the product can not be blank")
    @Column(length = 2000, nullable = false)
    private String description;

    @Column(length = 1000)
    private String model;

    @PositiveOrZero
    @Column(nullable = false)
    private int stockQuantity;

    @Column(precision = 19, scale = 4, nullable = false)
    private BigDecimal salePrice;

    @Column(precision = 19, scale = 4, nullable = false)
    private BigDecimal supplierCost;

    @ElementCollection
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "product_id"))
    @Size(max = 5, message = "A product can not have more than 5 images")
    @Valid
    private List<ProductImage> productImages = new ArrayList<>();

    @ElementCollection
    private Set<String> tags = new HashSet<>();

    private boolean active = true;

    @ManyToMany
    @JoinTable(
            name = "product_categories",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = false)
    private SupplierProfile supplierProfile;

    public void addCategory(Category category){
        categories.add(category);
        Set<Product> products = category.getProducts();
        products.add(this);
    }
}
