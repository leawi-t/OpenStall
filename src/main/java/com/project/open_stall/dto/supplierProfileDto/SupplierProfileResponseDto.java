package com.project.open_stall.dto.supplierProfileDto;

import com.project.open_stall.dto.addressDto.AddressDto;

public record SupplierProfileResponseDto(
        String bio,
        String companyName,
        AddressDto address
) {
}
