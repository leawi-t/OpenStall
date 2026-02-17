package com.project.open_stall.dto.addressDto;

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

