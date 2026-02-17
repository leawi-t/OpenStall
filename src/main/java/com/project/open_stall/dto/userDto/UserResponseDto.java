package com.project.open_stall.dto.userDto;

import com.project.open_stall.dto.supplierProfileDto.SupplierProfileResponseDto;

public record UserResponseDto(
        long id,
        String userName,
        String email,
        String role,
        SupplierProfileResponseDto supplierProfile
) {
}
