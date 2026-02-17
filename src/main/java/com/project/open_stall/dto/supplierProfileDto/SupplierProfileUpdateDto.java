package com.project.open_stall.dto.supplierProfileDto;

import com.project.open_stall.dto.SocialMediaLinkDto;
import com.project.open_stall.dto.addressDto.AddressDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record SupplierProfileUpdateDto(

        @Size(max = 250, message = "Bio can not be longer than 250 character")
        String bio,

        @Size(max = 50, message = "Company name can not be longer than 50 characters")
        String companyName,

        @Valid
        AddressDto address,

        @Valid
        @Size(min = 1, message = "Suppliers must include at least one social media platform link")
        List<SocialMediaLinkDto> socialMediaLinks
) {
}
