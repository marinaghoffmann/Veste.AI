package com.vesteai.backend.domain.template;

import com.vesteai.backend.domain.model.Look;
import com.vesteai.backend.domain.model.Peca;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class GeracaoLookTemplate {

    public final Look gerar(List<Peca> pecasDoGuardaRoupa, String ocasiao) {
        List<Peca> disponiveis = filtrarDisponiveis(pecasDoGuardaRoupa);
        List<Peca> selecionadas = selecionarPecas(disponiveis, ocasiao);
        Look look = montarLook(selecionadas);
        aposMontagem(look, ocasiao);
        return look;
    }

    protected List<Peca> filtrarDisponiveis(List<Peca> pecas) {
        return pecas.stream().filter(Peca::isDisponivel).toList();
    }

    protected abstract List<Peca> selecionarPecas(List<Peca> disponiveis, String ocasiao);

    protected Look montarLook(List<Peca> selecionadas) {
        Set<Long> pecaIds = selecionadas.stream().map(Peca::getId).collect(Collectors.toSet());
        return new Look(null, "Look sugerido", pecaIds);
    }

    protected void aposMontagem(Look look, String ocasiao) {
    }
}
