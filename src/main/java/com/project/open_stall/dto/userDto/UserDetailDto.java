package com.project.open_stall.dto.userDto;

import com.project.open_stall.dto.supplierProfileDto.SupplierProfileDetailsDto;

public record UserDetailDto(
        long id,
        String userName,
        String firstName,
        String lastName,
        String email,
        String role,
        SupplierProfileDetailsDto supplierProfile
) {
}
