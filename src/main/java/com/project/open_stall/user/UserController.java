package com.project.open_stall.user;

import com.project.open_stall.supplierProfile.dto.*;
import com.project.open_stall.user.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

//TODO: remember to preAuthorize and make sure to check the user logged in can update his/her profile only

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    @GetMapping
    public ResponseEntity<PagedModel<UserResponseDto>> getUser(
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) LocalDateTime start,
            @RequestParam(required = false) LocalDateTime end,
            Pageable pageable
    ){
        Page<UserResponseDto> page = service.getUsers(pageable, active, email, username, start, end);
        return ResponseEntity.ok(new PagedModel<>(page));
    }

    @GetMapping("/supplierProfile/{userId}")
    public ResponseEntity<SupplierProfileResponseDto> getSupplierById(@PathVariable long userId){
        return ResponseEntity.ok(service.getUserById(userId));
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
    public ResponseEntity<SupplierProfileResponseDto> updateSupplierProfile(
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
