package com.project.open_stall.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

@Embeddable
public class OrderItemSnapshot {

    @PositiveOrZero
    @Column(nullable = false)
    private BigDecimal priceAtPurchase;

    @NotBlank
    private String productNameAtPurchase;

    @PositiveOrZero
    @Column(nullable = false)
    private BigDecimal supplierCostAtPurchase;
}
