package com.vesteai.backend.domain.model;

import java.util.HashSet;
import java.util.Set;

public class Look {

    private final Long id;
    private String nome;
    private boolean favorito;
    private final Set<Long> pecaIds;

    public Look(Long id, String nome, Set<Long> pecaIds) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Look precisa de um nome");
        }
        if (pecaIds == null || pecaIds.size() < 2) {
            throw new IllegalArgumentException("Um look precisa de ao menos duas peças");
        }
        this.id = id;
        this.nome = nome;
        this.pecaIds = new HashSet<>(pecaIds);
        this.favorito = false;
    }

    public void favoritar() {
        this.favorito = true;
    }

    public void desfavoritar() {
        this.favorito = false;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public boolean isFavorito() {
        return favorito;
    }

    public Set<Long> getPecaIds() {
        return Set.copyOf(pecaIds);
    }
}
