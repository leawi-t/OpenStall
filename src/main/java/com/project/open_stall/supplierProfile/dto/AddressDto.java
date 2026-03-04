package com.project.open_stall.supplierProfile.dto;

import jakarta.validation.constraints.NotBlank;

public record AddressDto (
        @NotBlank
        String country,

        @NotBlank
        String state,

        @NotBlank
        String city
){
}

