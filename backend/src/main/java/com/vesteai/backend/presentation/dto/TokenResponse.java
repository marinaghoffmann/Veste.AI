package com.vesteai.backend.presentation.dto;

public record TokenResponse(String token, String tipo) {

    public static TokenResponse de(String token) {
        return new TokenResponse(token, "Bearer");
    }
}
