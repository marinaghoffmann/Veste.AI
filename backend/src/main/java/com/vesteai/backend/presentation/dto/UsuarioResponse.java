package com.vesteai.backend.presentation.dto;

import com.vesteai.backend.domain.model.Usuario;

public record UsuarioResponse(Long id, String nome, String email) {

    public static UsuarioResponse de(Usuario usuario) {
        return new UsuarioResponse(usuario.getId(), usuario.getNome(), usuario.getEmail().getValor());
    }
}
