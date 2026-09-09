package com.vesteai.backend.presentation.dto;

import jakarta.validation.constraints.NotBlank;

public record RegistroRequest(
        @NotBlank(message = "Nome é obrigatório") String nome,
        @NotBlank(message = "E-mail é obrigatório") String email,
        @NotBlank(message = "Senha é obrigatória") String senha) {
}
