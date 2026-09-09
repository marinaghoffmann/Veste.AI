package com.vesteai.backend.application.usecase;

import com.vesteai.backend.application.exception.EmailJaCadastradoException;
import com.vesteai.backend.application.port.PasswordHasher;
import com.vesteai.backend.application.port.UsuarioRepository;
import com.vesteai.backend.domain.model.Usuario;
import com.vesteai.backend.domain.model.vo.Email;
import org.springframework.stereotype.Service;

@Service
public class RegistrarUsuarioUseCase {

    private static final int TAMANHO_MINIMO_SENHA = 8;

    private final UsuarioRepository usuarioRepository;
    private final PasswordHasher passwordHasher;

    public RegistrarUsuarioUseCase(UsuarioRepository usuarioRepository, PasswordHasher passwordHasher) {
        this.usuarioRepository = usuarioRepository;
        this.passwordHasher = passwordHasher;
    }

    public Usuario executar(String nome, String emailBruto, String senhaBruta) {
        if (senhaBruta == null || senhaBruta.length() < TAMANHO_MINIMO_SENHA) {
            throw new IllegalArgumentException("Senha deve ter ao menos " + TAMANHO_MINIMO_SENHA + " caracteres");
        }

        Email email = new Email(emailBruto);
        if (usuarioRepository.existePorEmail(email)) {
            throw new EmailJaCadastradoException(email.getValor());
        }

        Usuario usuario = new Usuario(null, nome, email, passwordHasher.hash(senhaBruta));
        return usuarioRepository.salvar(usuario);
    }
}
