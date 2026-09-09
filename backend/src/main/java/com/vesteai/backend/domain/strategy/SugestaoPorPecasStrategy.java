package com.vesteai.backend.domain.strategy;

import com.vesteai.backend.domain.model.Peca;
import com.vesteai.backend.domain.model.SugestaoLook;
import com.vesteai.backend.domain.model.vo.Categoria;

import java.util.List;
import java.util.Set;

public class SugestaoPorPecasStrategy implements SugestaoLookStrategy {

    @Override
    public SugestaoLook sugerir(List<Peca> pecasDisponiveis, String ocasiao) {
        Peca parteDeCima = buscarPorCategoria(pecasDisponiveis, Categoria.PARTE_DE_CIMA);
        Peca parteDeBaixo = buscarPorCategoria(pecasDisponiveis, Categoria.PARTE_DE_BAIXO);
        if (parteDeCima == null || parteDeBaixo == null) {
            throw new IllegalStateException("Guarda-roupa não tem peças suficientes para uma sugestão");
        }
        Set<Long> pecaIds = Set.of(parteDeCima.getId(), parteDeBaixo.getId());
        String justificativa = "Combinação de %s e %s a partir das peças cadastradas"
                .formatted(parteDeCima.getNome(), parteDeBaixo.getNome());
        return new SugestaoLook(null, ocasiao, justificativa, pecaIds);
    }

    private Peca buscarPorCategoria(List<Peca> pecas, Categoria categoria) {
        return pecas.stream()
                .filter(Peca::isDisponivel)
                .filter(peca -> peca.getCategoria() == categoria)
                .findFirst()
                .orElse(null);
    }
}
