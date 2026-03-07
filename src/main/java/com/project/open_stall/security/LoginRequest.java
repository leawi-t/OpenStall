package com.project.open_stall.security;

public record LoginRequest(
        String username,
        String password
) {
}
