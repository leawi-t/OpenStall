package com.project.open_stall.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class AddressSnapshot {

    @NotBlank(message = "Country can not be blank")
    private String country;

    @NotBlank(message = "State is required for buyer filtering")
    private String state;

    @NotBlank(message = "City is required for buyer filtering")
    private String city;
}
