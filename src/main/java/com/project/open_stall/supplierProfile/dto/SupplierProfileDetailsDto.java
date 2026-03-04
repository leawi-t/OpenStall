package com.project.open_stall.supplierProfile.dto;

import com.project.open_stall.product.dto.ProductResponseDto;
import com.project.open_stall.supplierProfile.model.SocialMediaLink;

import java.util.List;

public record SupplierProfileDetailsDto(
        String bio,
        String companyName,
        AddressDto address,
        List<ProductResponseDto> products,
        List<SocialMediaLink> socialMediaLinks
) {
}
