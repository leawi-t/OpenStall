package com.project.open_stall.dto.userDto;

import com.project.open_stall.dto.supplierProfileDto.SupplierProfileRequestDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRequestDto (

        @NotBlank(message = "First name can not be blank")
        @Size(max = 25, message = "First name can not be longer than 50 characters")
        String firstName,

        @NotBlank(message = "Last name can not be blank")
        @Size(max = 25, message = "Last name can not be longer than 50 characters")
        String lastName,

        @NotBlank(message = "Username can not be blank")
        @Size(max = 25, min = 2, message = "Username can not be longer than 50 or shorter than 2 characters")
        String userName,

        @NotBlank
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                message = "Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one digit, and one special character (@$!%*?&)")
        String password,

        @NotBlank
        @Email
        String email,

        @NotBlank
        String role,

        @Valid
        SupplierProfileRequestDto supplierProfile
){
}
