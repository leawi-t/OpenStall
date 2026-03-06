package com.project.open_stall.supplierProfile.dto;

import com.project.open_stall.supplierProfile.model.SocialMediaLink;

import java.util.List;

public record SupplierProfileResponseDto(
        String bio,
        String companyName,
        AddressDto address,
        List<SocialMediaLink> socialMediaLinks
) {
}
