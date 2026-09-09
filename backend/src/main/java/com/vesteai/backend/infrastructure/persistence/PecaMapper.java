package com.vesteai.backend.infrastructure.persistence;

import com.vesteai.backend.domain.model.Peca;
import com.vesteai.backend.domain.model.vo.Categoria;
import com.vesteai.backend.domain.model.vo.Cor;
import com.vesteai.backend.domain.model.vo.Estacao;

public class PecaMapper {

    private PecaMapper() {
    }

    public static Peca paraDominio(PecaEntity entity) {
        Peca peca = new Peca(
                entity.getId(),
                entity.getNome(),
                Categoria.valueOf(entity.getCategoria()),
                new Cor(entity.getCor()),
                Estacao.valueOf(entity.getEstacao()),
                entity.getFotoUrl());
        if (!entity.isDisponivel()) {
            peca.marcarIndisponivel();
        }
        peca.definirDono(entity.getDonoId());
        return peca;
    }

    public static PecaEntity paraEntidade(Peca peca) {
        PecaEntity entity = new PecaEntity();
        entity.setId(peca.getId());
        entity.setNome(peca.getNome());
        entity.setCategoria(peca.getCategoria().name());
        entity.setCor(peca.getCor().getNome());
        entity.setEstacao(peca.getEstacao().name());
        entity.setFotoUrl(peca.getFotoUrl());
        entity.setDisponivel(peca.isDisponivel());
        entity.setDonoId(peca.getDonoId());
        return entity;
    }
}
