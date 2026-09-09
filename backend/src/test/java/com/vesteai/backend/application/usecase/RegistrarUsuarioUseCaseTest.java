package com.vesteai.backend.application.usecase;

import com.vesteai.backend.application.exception.EmailJaCadastradoException;
import com.vesteai.backend.application.port.PasswordHasher;
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

class RegistrarUsuarioUseCaseTest {

    private final Map<String, Usuario> usuariosPorEmail = new HashMap<>();

    private final UsuarioRepository usuarioRepository = new UsuarioRepository() {
        @Override
        public Usuario salvar(Usuario usuario) {
            Usuario salvo = new Usuario(1L, usuario.getNome(), usuario.getEmail(), usuario.getSenhaHash());
            usuariosPorEmail.put(salvo.getEmail().getValor(), salvo);
            return salvo;
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

    private RegistrarUsuarioUseCase useCase;

    @BeforeEach
    void setUp() {
        usuariosPorEmail.clear();
        useCase = new RegistrarUsuarioUseCase(usuarioRepository, passwordHasher);
    }

    @Test
    void registraUsuarioComSenhaHasheada() {
        Usuario usuario = useCase.executar("Maria Silva", "maria@example.com", "senha1234");

        assertEquals("Maria Silva", usuario.getNome());
        assertEquals("maria@example.com", usuario.getEmail().getValor());
        assertEquals("hash(senha1234)", usuario.getSenhaHash());
    }

    @Test
    void rejeitaEmailJaCadastrado() {
        useCase.executar("Maria Silva", "maria@example.com", "senha1234");

        assertThrows(EmailJaCadastradoException.class,
                () -> useCase.executar("Outra Maria", "maria@example.com", "outrasenha"));
    }

    @Test
    void rejeitaSenhaCurta() {
        assertThrows(IllegalArgumentException.class,
                () -> useCase.executar("Maria Silva", "maria@example.com", "123"));
    }

    @Test
    void rejeitaEmailInvalido() {
        assertThrows(IllegalArgumentException.class,
                () -> useCase.executar("Maria Silva", "email-invalido", "senha1234"));
    }
}
