package com.vesteai.backend.presentation.dto;

import com.vesteai.backend.domain.model.vo.Categoria;
import com.vesteai.backend.domain.model.vo.Estacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PecaRequest(
        @NotBlank(message = "Nome é obrigatório") String nome,
        @NotNull(message = "Categoria é obrigatória") Categoria categoria,
        @NotBlank(message = "Cor é obrigatória") String cor,
        @NotNull(message = "Estação é obrigatória") Estacao estacao,
        @NotBlank(message = "Foto é obrigatória") String fotoUrl) {
}
