package com.vesteai.backend.domain.decorator;

import com.vesteai.backend.domain.model.Peca;
import com.vesteai.backend.domain.model.vo.Categoria;
import com.vesteai.backend.domain.model.vo.Cor;
import com.vesteai.backend.domain.model.vo.Estacao;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PecaDecoratorTest {

    private final Peca peca = new Peca(1L, "Camisa branca", Categoria.PARTE_DE_CIMA, new Cor("branco"), Estacao.TODAS, null);

    @Test
    void combinaMultiplosDecoradoresSemAlterarAPeca() {
        PecaVisual visual = new PecaEmColecaoDecorator(
                new PecaFavoritaDecorator(new PecaBase(peca)),
                "verão");

        assertEquals(2, visual.getAtributosExtras().size());
        assertTrue(visual.getAtributosExtras().contains("favorito"));
        assertTrue(visual.getAtributosExtras().contains("colecao:verão"));
        assertEquals(peca, visual.getPeca());
    }
}
