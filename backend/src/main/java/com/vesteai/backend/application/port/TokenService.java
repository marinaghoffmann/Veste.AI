package com.vesteai.backend.application.port;

import com.vesteai.backend.domain.model.Usuario;

public interface TokenService {

    String gerarToken(Usuario usuario);
}
