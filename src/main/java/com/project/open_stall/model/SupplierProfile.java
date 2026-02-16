package com.project.open_stall.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class SupplierProfile {

    @Id
    private Long id;

    private String companyName;

    @Column(length = 2000, nullable = false)
    private String bio;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "supplierProfile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();

    @OneToOne(mappedBy = "supplierProfile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Column(nullable = false)
    @Valid
    private Address address;

    @ElementCollection
    @Size(min = 1)
    @CollectionTable(name = "social_media_links", joinColumns = @JoinColumn(name = "supplier_id"))
    @Valid
    List<SocialMediaLink> socialMediaLinks;

    public void setAddress(Address address) {
        this.address = address;
        if (address != null) {
            address.setSupplierProfile(this);
        }
    }
}
