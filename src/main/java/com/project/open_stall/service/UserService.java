package com.project.open_stall.service;

import com.project.open_stall.dto.userDto.UserRequestDto;
import com.project.open_stall.dto.userDto.UserResponseDto;
import com.project.open_stall.exception.InvalidOperationException;
import com.project.open_stall.mapper.UserMapper;
import com.project.open_stall.model.User;
import com.project.open_stall.repo.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo repo;
    private final UserMapper mapper;

    @Transactional
    public UserResponseDto registerUser(UserRequestDto dto){
        if (repo.existsByEmail(dto.email())){
            throw new InvalidOperationException("Email already in use");
        }

        User user = mapper.toEntity(dto);

        if (dto.role().equalsIgnoreCase("Supplier")){
            if (dto.supplierProfile() == null)
                throw new InvalidOperationException("Supplier profile data is required for Supplier role");
            else
                user.setSupplierProfile(null);
        }

        User savedUser = repo.save(user);
        return mapper.toResponse(savedUser);
    }

}
