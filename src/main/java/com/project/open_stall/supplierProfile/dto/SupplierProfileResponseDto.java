package com.project.open_stall.supplierProfile.dto;

public record SupplierProfileResponseDto(
        String bio,
        String companyName,
        AddressDto address
) {
}
