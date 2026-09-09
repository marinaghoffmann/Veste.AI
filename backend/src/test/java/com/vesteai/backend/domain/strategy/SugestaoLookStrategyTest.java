package com.vesteai.backend.domain.strategy;

import com.vesteai.backend.domain.model.Peca;
import com.vesteai.backend.domain.model.SugestaoLook;
import com.vesteai.backend.domain.model.vo.Categoria;
import com.vesteai.backend.domain.model.vo.Cor;
import com.vesteai.backend.domain.model.vo.Estacao;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SugestaoLookStrategyTest {

    private final Peca camisa = new Peca(1L, "Camisa branca", Categoria.PARTE_DE_CIMA, new Cor("branco"), Estacao.TODAS, null);
    private final Peca calca = new Peca(2L, "Calça jeans", Categoria.PARTE_DE_BAIXO, new Cor("azul"), Estacao.TODAS, null);

    @Test
    void sugestaoPorPecasCombinaCimaEBaixo() {
        SugestaoLookStrategy strategy = new SugestaoPorPecasStrategy();

        SugestaoLook sugestao = strategy.sugerir(List.of(camisa, calca), "casual");

        assertEquals(2, sugestao.getPecaIds().size());
    }

    @Test
    void sugestaoPorOcasiaoRespeitaEstacao() {
        Peca camisaVerao = new Peca(3L, "Camisa leve", Categoria.PARTE_DE_CIMA, new Cor("branco"), Estacao.VERAO, null);
        Peca bermuda = new Peca(4L, "Bermuda", Categoria.PARTE_DE_BAIXO, new Cor("azul"), Estacao.VERAO, null);
        SugestaoLookStrategy strategy = new SugestaoPorOcasiaoStrategy();

        SugestaoLook sugestao = strategy.sugerir(List.of(camisaVerao, bermuda), "praia");

        assertEquals(2, sugestao.getPecaIds().size());
    }
}
