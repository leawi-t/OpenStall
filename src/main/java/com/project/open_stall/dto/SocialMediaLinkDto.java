package com.project.open_stall.dto;

import jakarta.validation.constraints.NotBlank;

public record SocialMediaLinkDto(
        @NotBlank
        String platform,

        @NotBlank
        String url,

        boolean verified
) {
}
