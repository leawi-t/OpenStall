package com.project.open_stall.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// TODO: add better validations for the names

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "first name can not be blank")
    @Column(nullable = false, length = 25)
    private String firstName;

    @NotBlank(message = "last name can not be blank")
    @Column(nullable = false, length = 25)
    private String lastName;

    @NotBlank(message = "Username can not be blank")
    @Column(nullable = false, length = 25, unique = true)
    private String userName;

    @NotBlank(message = "Email can not be blank")
    @Email(message = "Please use a valid email address")
    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one digit, and one special character (@$!%*?&)")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private SupplierProfile supplierProfile;
}

