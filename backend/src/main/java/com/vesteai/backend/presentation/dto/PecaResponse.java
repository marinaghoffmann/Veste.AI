package com.vesteai.backend.presentation.dto;

import com.vesteai.backend.domain.model.Peca;

public record PecaResponse(
        Long id,
        String nome,
        String categoria,
        String cor,
        String estacao,
        String fotoUrl,
        boolean disponivel) {

    public static PecaResponse de(Peca peca) {
        return new PecaResponse(
                peca.getId(),
                peca.getNome(),
                peca.getCategoria().name(),
                peca.getCor().getNome(),
                peca.getEstacao().name(),
                peca.getFotoUrl(),
                peca.isDisponivel());
    }
}
