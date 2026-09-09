package com.vesteai.backend.infrastructure.persistence;

import com.vesteai.backend.domain.model.Usuario;
import com.vesteai.backend.domain.model.vo.Email;

public class UsuarioMapper {

    private UsuarioMapper() {
    }

    public static Usuario paraDominio(UsuarioEntity entity) {
        return new Usuario(entity.getId(), entity.getNome(), new Email(entity.getEmail()), entity.getSenhaHash());
    }

    public static UsuarioEntity paraEntidade(Usuario usuario) {
        UsuarioEntity entity = new UsuarioEntity();
        entity.setId(usuario.getId());
        entity.setNome(usuario.getNome());
        entity.setEmail(usuario.getEmail().getValor());
        entity.setSenhaHash(usuario.getSenhaHash());
        return entity;
    }
}
