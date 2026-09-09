package com.vesteai.backend.application.port;

import com.vesteai.backend.domain.model.Usuario;
import com.vesteai.backend.domain.model.vo.Email;

import java.util.Optional;

public interface UsuarioRepository {

    Usuario salvar(Usuario usuario);

    Optional<Usuario> buscarPorEmail(Email email);

    boolean existePorEmail(Email email);
}
