package com.project.open_stall.supplierProfile.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class SocialMediaLink {

    @NotBlank
    private String platform;

    @NotBlank
    private String url;

    private boolean verified;

    public SocialMediaLink(String platform, String url) {
        this.platform = platform;
        this.url = url;
    }
}
