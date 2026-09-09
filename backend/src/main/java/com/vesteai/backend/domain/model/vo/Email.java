package com.vesteai.backend.domain.model.vo;

import java.util.Objects;
import java.util.regex.Pattern;

public final class Email {

    private static final Pattern FORMATO = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

    private final String valor;

    public Email(String valor) {
        if (valor == null || !FORMATO.matcher(valor).matches()) {
            throw new IllegalArgumentException("E-mail inválido: " + valor);
        }
        this.valor = valor.trim().toLowerCase();
    }

    public String getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Email other)) return false;
        return valor.equals(other.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public String toString() {
        return valor;
    }
}
