package com.vesteai.backend.domain.model;

import com.vesteai.backend.domain.model.vo.Email;

public class Usuario {

    private final Long id;
    private String nome;
    private Email email;
    private String senhaHash;

    public Usuario(Long id, String nome, Email email, String senhaHash) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Usuário precisa de um nome");
        }
        if (email == null) {
            throw new IllegalArgumentException("Usuário precisa de um e-mail");
        }
        if (senhaHash == null || senhaHash.isBlank()) {
            throw new IllegalArgumentException("Usuário precisa de uma senha");
        }
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senhaHash = senhaHash;
    }

    public void editarPerfil(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Email getEmail() {
        return email;
    }

    public String getSenhaHash() {
        return senhaHash;
    }
}
