package com.vesteai.backend.infrastructure.persistence;

import com.vesteai.backend.application.port.UsuarioRepository;
import com.vesteai.backend.domain.model.Usuario;
import com.vesteai.backend.domain.model.vo.Email;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UsuarioRepositoryAdapter implements UsuarioRepository {

    private final UsuarioJpaRepository jpaRepository;

    public UsuarioRepositoryAdapter(UsuarioJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Usuario salvar(Usuario usuario) {
        UsuarioEntity salvo = jpaRepository.save(UsuarioMapper.paraEntidade(usuario));
        return UsuarioMapper.paraDominio(salvo);
    }

    @Override
    public Optional<Usuario> buscarPorEmail(Email email) {
        return jpaRepository.findByEmail(email.getValor()).map(UsuarioMapper::paraDominio);
    }

    @Override
    public boolean existePorEmail(Email email) {
        return jpaRepository.existsByEmail(email.getValor());
    }
}
