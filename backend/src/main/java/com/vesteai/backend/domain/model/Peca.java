package com.vesteai.backend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Peca {
    private Long id;
    private String nome;
    private String categoria;
    private String cor;
    private String estacao;
    private String fotoUrl;
    private boolean disponivel;
}