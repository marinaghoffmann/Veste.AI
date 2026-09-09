package com.vesteai.backend.infrastructure.persistence;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "colecoes")
@Getter
@Setter
public class ColecaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;

    @ElementCollection
    @CollectionTable(name = "colecao_pecas", joinColumns = @JoinColumn(name = "colecao_id"))
    @Column(name = "peca_id")
    private Set<Long> pecaIds = new HashSet<>();
}
