package com.project.open_stall.security;

import com.project.open_stall.cart.model.Cart;
import com.project.open_stall.common.exception.InvalidOperationException;
import com.project.open_stall.supplierProfile.SupplierProfileMapper;
import com.project.open_stall.user.Role;
import com.project.open_stall.user.User;
import com.project.open_stall.user.UserMapper;
import com.project.open_stall.user.UserRepo;
import com.project.open_stall.user.dto.UserDetailDto;
import com.project.open_stall.user.dto.UserRequestDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final SupplierProfileMapper profileMapper;
    private final JWTService jwtService;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;

    @Transactional
    public UserDetailDto registerUser(UserRequestDto dto){
        if (userRepo.existsByEmail(dto.email())){
            throw new InvalidOperationException("Email already in use");
        }

        if (userRepo.existsByUserName(dto.userName())){
            throw new InvalidOperationException("Username already in use");
        }

        User user = userMapper.toEntity(dto);
        Cart cart = new Cart();
        user.setCart(cart);

        if (Role.SUPPLIER.name().equalsIgnoreCase(dto.role())){
            if (dto.supplierProfile() == null)
                throw new InvalidOperationException("Supplier profile data is required for Supplier role");
            else
                user.setSupplierProfile(profileMapper.toEntity(dto.supplierProfile()));
        }

        else{
            if (dto.supplierProfile() != null)
                throw new InvalidOperationException("Consumer roles can not have Supplier Profiles");
            else
                user.setSupplierProfile(null);
        }

        user.setPassword(encoder.encode(user.getPassword()));
        User savedUser = userRepo.save(user);
        return userMapper.toDetail(savedUser);
    }

    public String verifyUser(LoginRequest dto){
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.username(), dto.password())
        );

        return jwtService.generateToken(dto.username());
    }
}
