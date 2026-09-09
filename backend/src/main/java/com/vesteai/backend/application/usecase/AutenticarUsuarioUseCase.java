package com.vesteai.backend.application.usecase;

import com.vesteai.backend.application.exception.CredenciaisInvalidasException;
import com.vesteai.backend.application.port.PasswordHasher;
import com.vesteai.backend.application.port.TokenService;
import com.vesteai.backend.application.port.UsuarioRepository;
import com.vesteai.backend.domain.model.Usuario;
import com.vesteai.backend.domain.model.vo.Email;
import org.springframework.stereotype.Service;

@Service
public class AutenticarUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;
    private final PasswordHasher passwordHasher;
    private final TokenService tokenService;

    public AutenticarUsuarioUseCase(
            UsuarioRepository usuarioRepository, PasswordHasher passwordHasher, TokenService tokenService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordHasher = passwordHasher;
        this.tokenService = tokenService;
    }

    public String executar(String emailBruto, String senhaBruta) {
        Email email;
        try {
            email = new Email(emailBruto);
        } catch (IllegalArgumentException e) {
            throw new CredenciaisInvalidasException();
        }

        Usuario usuario = usuarioRepository.buscarPorEmail(email)
                .orElseThrow(CredenciaisInvalidasException::new);

        if (!passwordHasher.confere(senhaBruta, usuario.getSenhaHash())) {
            throw new CredenciaisInvalidasException();
        }

        return tokenService.gerarToken(usuario);
    }
}
