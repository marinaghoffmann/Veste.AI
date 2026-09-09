package com.vesteai.backend.application.usecase;

import com.vesteai.backend.application.exception.CredenciaisInvalidasException;
import com.vesteai.backend.application.port.PasswordHasher;
import com.vesteai.backend.application.port.TokenService;
import com.vesteai.backend.application.port.UsuarioRepository;
import com.vesteai.backend.domain.model.Usuario;
import com.vesteai.backend.domain.model.vo.Email;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AutenticarUsuarioUseCaseTest {

    private final Map<String, Usuario> usuariosPorEmail = new HashMap<>();

    private final UsuarioRepository usuarioRepository = new UsuarioRepository() {
        @Override
        public Usuario salvar(Usuario usuario) {
            usuariosPorEmail.put(usuario.getEmail().getValor(), usuario);
            return usuario;
        }

        @Override
        public Optional<Usuario> buscarPorEmail(Email email) {
            return Optional.ofNullable(usuariosPorEmail.get(email.getValor()));
        }

        @Override
        public boolean existePorEmail(Email email) {
            return usuariosPorEmail.containsKey(email.getValor());
        }
    };

    private final PasswordHasher passwordHasher = new PasswordHasher() {
        @Override
        public String hash(String senhaBruta) {
            return "hash(" + senhaBruta + ")";
        }

        @Override
        public boolean confere(String senhaBruta, String senhaHash) {
            return hash(senhaBruta).equals(senhaHash);
        }
    };

    private final TokenService tokenService = usuario -> "token-para-" + usuario.getEmail().getValor();

    private AutenticarUsuarioUseCase useCase;

    @BeforeEach
    void setUp() {
        usuariosPorEmail.clear();
        usuarioRepository.salvar(
                new Usuario(1L, "Maria Silva", new Email("maria@example.com"), passwordHasher.hash("senha1234")));
        useCase = new AutenticarUsuarioUseCase(usuarioRepository, passwordHasher, tokenService);
    }

    @Test
    void autenticaComCredenciaisValidasEGeraToken() {
        String token = useCase.executar("maria@example.com", "senha1234");

        assertEquals("token-para-maria@example.com", token);
    }

    @Test
    void rejeitaSenhaIncorreta() {
        assertThrows(CredenciaisInvalidasException.class, () -> useCase.executar("maria@example.com", "senhaerrada"));
    }

    @Test
    void rejeitaEmailNaoCadastrado() {
        assertThrows(CredenciaisInvalidasException.class,
                () -> useCase.executar("outra@example.com", "senha1234"));
    }
}
