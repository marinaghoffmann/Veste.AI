package com.vesteai.backend.domain.model.vo;

import java.util.Objects;

public final class Cor {

    private final String nome;

    public Cor(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Cor não pode ser vazia");
        }
        this.nome = nome.trim().toLowerCase();
    }

    public String getNome() {
        return nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cor other)) return false;
        return nome.equals(other.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }

    @Override
    public String toString() {
        return nome;
    }
}
