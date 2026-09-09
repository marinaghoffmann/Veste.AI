package com.vesteai.backend.domain.strategy;

import com.vesteai.backend.domain.model.Peca;
import com.vesteai.backend.domain.model.SugestaoLook;
import com.vesteai.backend.domain.model.vo.Categoria;
import com.vesteai.backend.domain.model.vo.Estacao;

import java.util.List;
import java.util.Set;

public class SugestaoPorOcasiaoStrategy implements SugestaoLookStrategy {

    @Override
    public SugestaoLook sugerir(List<Peca> pecasDisponiveis, String ocasiao) {
        Estacao estacaoAlvo = "praia".equalsIgnoreCase(ocasiao) ? Estacao.VERAO : Estacao.TODAS;

        Peca parteDeCima = buscarPorCategoriaEEstacao(pecasDisponiveis, Categoria.PARTE_DE_CIMA, estacaoAlvo);
        Peca parteDeBaixo = buscarPorCategoriaEEstacao(pecasDisponiveis, Categoria.PARTE_DE_BAIXO, estacaoAlvo);
        if (parteDeCima == null || parteDeBaixo == null) {
            throw new IllegalStateException("Não há peças compatíveis com a ocasião '%s'".formatted(ocasiao));
        }

        Set<Long> pecaIds = Set.of(parteDeCima.getId(), parteDeBaixo.getId());
        String justificativa = "Sugestão para '%s': %s combinado com %s"
                .formatted(ocasiao, parteDeCima.getNome(), parteDeBaixo.getNome());
        return new SugestaoLook(null, ocasiao, justificativa, pecaIds);
    }

    private Peca buscarPorCategoriaEEstacao(List<Peca> pecas, Categoria categoria, Estacao estacao) {
        return pecas.stream()
                .filter(Peca::isDisponivel)
                .filter(peca -> peca.getCategoria() == categoria)
                .filter(peca -> estacao == Estacao.TODAS || peca.getEstacao() == estacao || peca.getEstacao() == Estacao.TODAS)
                .findFirst()
                .orElse(null);
    }
}
