package com.project.open_stall.dto.supplierProfileDto;

import com.project.open_stall.dto.SocialMediaLinkDto;
import com.project.open_stall.dto.addressDto.AddressDto;
import com.project.open_stall.dto.productDto.ProductResponseDto;

import java.util.List;

public record SupplierProfileDetailsDto(
        String bio,
        String companyName,
        AddressDto address,
        List<ProductResponseDto> products,
        List<SocialMediaLinkDto> socialMediaLinks
) {
}
