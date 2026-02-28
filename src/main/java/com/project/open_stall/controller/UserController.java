package com.project.open_stall.controller;

import com.project.open_stall.dto.supplierProfileDto.*;
import com.project.open_stall.dto.userDto.*;
import com.project.open_stall.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//TODO: remember to preAuthorize and make sure to check the user logged in can update his/her profile only

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    @GetMapping
    public ResponseEntity<PagedModel<UserResponseDto>> getAllUsers(Pageable pageable){
        Page<UserResponseDto> page = service.getAllUsers(pageable);
        return new ResponseEntity<>(new PagedModel<>(page), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDetailDto> getUserById(@PathVariable long userId){
        return new ResponseEntity<>(service.getUserById(userId), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<UserDetailDto> searchUser(@RequestParam String username){
        return new ResponseEntity<>(service.searchUser(username), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDetailDto> registerUser(@RequestBody @Valid UserRequestDto dto){
        return new ResponseEntity<>(service.registerUser(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDetailDto> updateUser(
            @RequestBody @Valid UserUpdateDto dto,
             @PathVariable long userId
    ){
        return new ResponseEntity<>(service.updateUser(dto, userId), HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable long userId){
        service.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}")
    public ResponseEntity<UserDetailDto> addSupplierProfile(
            @RequestBody @Valid SupplierProfileRequestDto dto,
            @PathVariable long userId
    ){
        return new ResponseEntity<>(service.addSupplierProfile(dto, userId), HttpStatus.CREATED);
    }

    @PutMapping("/{userId}/supplierProfile")
    public ResponseEntity<SupplierProfileDetailsDto> updateSupplierProfile(
            @RequestBody @Valid SupplierProfileUpdateDto dto,
            @PathVariable long userId
    ){
        return new ResponseEntity<>(service.updateSupplierProfile(dto, userId), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/supplierProfile")
    public ResponseEntity<Void> deleteSupplierProfile(@PathVariable long userId){
        service.deleteSupplierProfile(userId);
        return ResponseEntity.noContent().build();
    }
}
