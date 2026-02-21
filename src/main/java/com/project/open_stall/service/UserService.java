package com.project.open_stall.service;

import com.project.open_stall.dto.supplierProfileDto.*;
import com.project.open_stall.dto.userDto.*;
import com.project.open_stall.exception.*;
import com.project.open_stall.mapper.SupplierProfileMapper;
import com.project.open_stall.mapper.UserMapper;
import com.project.open_stall.model.Role;
import com.project.open_stall.model.User;
import com.project.open_stall.repo.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final SupplierProfileMapper profileMapper;

    public List<UserResponseDto> getAllUsers(){
        return userMapper.toResponseList(userRepo.findAll());
    }

    public UserDetailDto getUserById(long id){
        return userMapper.toDetail(userRepo.findById(id).
                orElseThrow(()-> new ResourceNotFoundException("User with " + id + " does not exist")));
    }

    // TODO: Instead of just username probably better to create a filter
    public UserDetailDto getUserByUsername(String userName){
        return userMapper.toDetail(userRepo.findByUserName(userName)
                .orElseThrow(() -> new ResourceNotFoundException(userName + " does not exist")));
    }

    @Transactional
    public UserResponseDto registerUser(UserRequestDto dto){
        if (userRepo.existsByEmail(dto.email())){
            throw new InvalidOperationException("Email already in use");
        }

        if (userRepo.existsByUserName(dto.userName())){
            throw new InvalidOperationException("Username already in use");
        }

        User user = userMapper.toEntity(dto);

        if (dto.role().equalsIgnoreCase("Supplier")){
            if (dto.supplierProfile() == null)
                throw new InvalidOperationException("Supplier profile data is required for Supplier role");
            else
                user.setSupplierProfile(null);
        }

        User savedUser = userRepo.save(user);
        return userMapper.toResponse(savedUser);
    }

    @Transactional
    public UserDetailDto addSupplierProfile(SupplierProfileRequestDto dto, long userId) {
        User user = userRepo.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User with " + userId + " does not exist"));

        user.setRole(Role.valueOf("SUPPLIER"));
        user.setSupplierProfile(profileMapper.toEntity(dto));

        return userMapper.toDetail(userRepo.save(user));
    }
}
