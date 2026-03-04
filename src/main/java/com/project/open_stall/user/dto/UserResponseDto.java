package com.project.open_stall.user.dto;

import com.project.open_stall.supplierProfile.dto.SupplierProfileResponseDto;

public record UserResponseDto(
        long id,
        String userName,
        String email,
        String role,
        SupplierProfileResponseDto supplierProfile
) {
}
