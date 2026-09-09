package com.vesteai.backend.domain.model;

import java.util.HashSet;
import java.util.Set;

public class Colecao {

    private final Long id;
    private String nome;
    private String descricao;
    private final Set<Long> pecaIds = new HashSet<>();

    public Colecao(Long id, String nome, String descricao) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Coleção precisa de um nome");
        }
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }

    public void adicionarPeca(Long pecaId) {
        pecaIds.add(pecaId);
    }

    public void removerPeca(Long pecaId) {
        pecaIds.remove(pecaId);
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Set<Long> getPecaIds() {
        return Set.copyOf(pecaIds);
    }
}
