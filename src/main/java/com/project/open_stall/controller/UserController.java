package com.project.open_stall.controller;

import com.project.open_stall.dto.supplierProfileDto.SupplierProfileDetailsDto;
import com.project.open_stall.dto.supplierProfileDto.SupplierProfileRequestDto;
import com.project.open_stall.dto.supplierProfileDto.SupplierProfileUpdateDto;
import com.project.open_stall.dto.userDto.UserDetailDto;
import com.project.open_stall.dto.userDto.UserRequestDto;
import com.project.open_stall.dto.userDto.UserResponseDto;
import com.project.open_stall.dto.userDto.UserUpdateDto;
import com.project.open_stall.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//TODO: remember to preAuthorize and make sure to check the user logged in can update his/her profile only

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service){ this.service = service; }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers(){
        return new ResponseEntity<>(service.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{userid}")
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
