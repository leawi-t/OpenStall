package com.project.open_stall.order.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
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
