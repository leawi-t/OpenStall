package com.project.open_stall.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity

// TODO: use Address auto-complete api for address validation

public class Address {

    @Id
    private long id;

    @NotBlank(message = "Country can not be blank")
    private String country;

    @NotBlank(message = "State is required for buyer filtering")
    private String state;

    @NotBlank(message = "city is required for buyer filtering")
    private String city;

    @OneToOne
    @MapsId
    @JoinColumn(name = "supplier_id")
    private SupplierProfile supplierProfile;
}
