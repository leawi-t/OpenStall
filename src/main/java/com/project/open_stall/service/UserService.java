package com.project.open_stall.service;

import com.project.open_stall.dto.supplierProfileDto.*;
import com.project.open_stall.dto.userDto.*;
import com.project.open_stall.exception.*;
import com.project.open_stall.mapper.SupplierProfileMapper;
import com.project.open_stall.mapper.UserMapper;
import com.project.open_stall.model.Cart;
import com.project.open_stall.model.Role;
import com.project.open_stall.model.User;
import com.project.open_stall.repo.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final SupplierProfileMapper profileMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public Page<UserResponseDto> getAllUsers(Pageable pageable){
        Page<User> page = userRepo.findAll(pageable);
        return page.map(userMapper::toResponse);
    }

    public UserDetailDto getUserById(long id){
        return userMapper.toDetail(userRepo.findById(id).
                orElseThrow(()-> new ResourceNotFoundException("User with " + id + " does not exist")));
    }

    public UserDetailDto searchUser(String userName){
        return userMapper.toDetail(userRepo.findByUserName(userName)
                .orElseThrow(() -> new ResourceNotFoundException(userName + " does not exist")));
    }

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

        if (dto.role().equalsIgnoreCase("Supplier")){
            if (dto.supplierProfile() == null)
                throw new InvalidOperationException("Supplier profile data is required for Supplier role");
            else
                user.setSupplierProfile(null);
        }

        else{
            if (dto.supplierProfile() != null)
                throw new InvalidOperationException("Consumer roles can not have Supplier Profiles");
            else
                user.setSupplierProfile(null);
        }

        User savedUser = userRepo.save(user);
        return userMapper.toDetail(savedUser);
    }

    @Transactional
    public UserDetailDto updateUser(UserUpdateDto dto, long userId){
        User user = userRepo.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User with " + userId + " does not exist"));

        userMapper.updateEntity(dto, user);
        return userMapper.toDetail(user);
    }

    @Transactional
    public void deleteUser(long userId){
        if (!userRepo.existsById(userId))
            throw new ResourceNotFoundException("User with " + userId + " does not exist");
        userRepo.deleteById(userId);
    }

    @Transactional
    public UserDetailDto addSupplierProfile(SupplierProfileRequestDto dto, long userId) {
        User user = userRepo.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User with " + userId + " does not exist"));

        if (user.getRole() == Role.SUPPLIER){
            throw new InvalidOperationException("Already have an associated Supplier profile");
        }

        user.setRole(Role.valueOf("SUPPLIER"));
        user.setSupplierProfile(profileMapper.toEntity(dto));

        return userMapper.toDetail(userRepo.save(user));
    }

    @Transactional
    @PreAuthorize("hasRole('SUPPLIER')")
    public SupplierProfileDetailsDto updateSupplierProfile(SupplierProfileUpdateDto dto, long userId){
        User user = userRepo.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User with " + userId + " does not exist"));

        profileMapper.updateEntity(dto, user.getSupplierProfile());
        return profileMapper.toDetails(user.getSupplierProfile());
    }

    @Transactional
    @PreAuthorize("hasRole('SUPPLIER')")
    public void deleteSupplierProfile(long userId){
        User user = userRepo.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User with " + userId + " does not exist"));

        user.setSupplierProfile(null);
        user.setRole(Role.valueOf("CONSUMER"));
    }
}
