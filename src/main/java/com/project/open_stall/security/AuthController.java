package com.project.open_stall.security;

import com.project.open_stall.user.dto.UserDetailDto;
import com.project.open_stall.user.dto.UserRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<UserDetailDto> registerUser(@RequestBody @Valid UserRequestDto dto){
        return new ResponseEntity<>(service.registerUser(dto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest dto){
        return service.verifyUser(dto);
    }
}