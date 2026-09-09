package com.vesteai.backend.domain.template;

import com.vesteai.backend.domain.event.LookEventPublisher;
import com.vesteai.backend.domain.model.Look;
import com.vesteai.backend.domain.model.Peca;
import com.vesteai.backend.domain.model.vo.Categoria;
import com.vesteai.backend.domain.model.vo.Estacao;

import java.util.List;

public class GeracaoLookPorOcasiaoTemplate extends GeracaoLookTemplate {

    private final LookEventPublisher eventPublisher;

    public GeracaoLookPorOcasiaoTemplate(LookEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    protected List<Peca> selecionarPecas(List<Peca> disponiveis, String ocasiao) {
        Estacao estacaoAlvo = "praia".equalsIgnoreCase(ocasiao) ? Estacao.VERAO : Estacao.TODAS;
        return disponiveis.stream()
                .filter(peca -> peca.getCategoria() == Categoria.PARTE_DE_CIMA || peca.getCategoria() == Categoria.PARTE_DE_BAIXO)
                .filter(peca -> estacaoAlvo == Estacao.TODAS || peca.getEstacao() == estacaoAlvo || peca.getEstacao() == Estacao.TODAS)
                .limit(2)
                .toList();
    }

    @Override
    protected void aposMontagem(Look look, String ocasiao) {
        eventPublisher.notificarLookCriado(look);
    }
}
