package com.project.open_stall.user.dto;

import com.project.open_stall.supplierProfile.dto.SupplierProfileResponseDto;

public record UserDetailDto(
        long id,
        String userName,
        String firstName,
        String lastName,
        String email,
        String role,
        SupplierProfileResponseDto supplierProfile
) {
}
