package com.project.open_stall.dto.userDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserUpdateDto(

        @Size(max = 25, message = "First name can not be longer than 50 characters")
        String firstName,

        @Size(max = 25, message = "Last name can not be longer than 50 characters")
        String lastName,

        @Size(max = 25, min = 2, message = "Username can not be longer than 50 or shorter than 2 characters")
        String userName,

        @Email
        String email

) {
}
