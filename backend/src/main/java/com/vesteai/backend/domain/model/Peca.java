package com.vesteai.backend.domain.model;

import com.vesteai.backend.domain.model.vo.Categoria;
import com.vesteai.backend.domain.model.vo.Cor;
import com.vesteai.backend.domain.model.vo.Estacao;

public class Peca {

    private final Long id;
    private String nome;
    private Categoria categoria;
    private Cor cor;
    private Estacao estacao;
    private String fotoUrl;
    private boolean disponivel;
    private Long donoId;

    public Peca(Long id, String nome, Categoria categoria, Cor cor, Estacao estacao, String fotoUrl) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Peça precisa de um nome");
        }
        if (categoria == null || cor == null || estacao == null) {
            throw new IllegalArgumentException("Peça precisa de categoria, cor e estação");
        }
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.cor = cor;
        this.estacao = estacao;
        this.fotoUrl = fotoUrl;
        this.disponivel = true;
    }

    public void marcarIndisponivel() {
        this.disponivel = false;
    }

    public void marcarDisponivel() {
        this.disponivel = true;
    }

    public void definirDono(Long donoId) {
        this.donoId = donoId;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Cor getCor() {
        return cor;
    }

    public Estacao getEstacao() {
        return estacao;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public Long getDonoId() {
        return donoId;
    }
}
