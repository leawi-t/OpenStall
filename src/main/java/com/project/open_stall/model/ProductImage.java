package com.project.open_stall.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class ProductImage {

    @NotBlank
    private String url;

    @NotBlank
    @Size(max = 1000)
    private String altText;

    private int order;
}

