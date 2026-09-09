package com.vesteai.backend.domain.template;

import com.vesteai.backend.domain.event.LookEventPublisher;
import com.vesteai.backend.domain.model.Look;
import com.vesteai.backend.domain.model.Peca;
import com.vesteai.backend.domain.model.vo.Categoria;
import com.vesteai.backend.domain.model.vo.Cor;
import com.vesteai.backend.domain.model.vo.Estacao;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class GeracaoLookTemplateTest {

    @Test
    void geraLookIgnorandoPecasIndisponiveis() {
        Peca camisa = new Peca(1L, "Camisa", Categoria.PARTE_DE_CIMA, new Cor("branco"), Estacao.TODAS, null);
        Peca calca = new Peca(2L, "Calça", Categoria.PARTE_DE_BAIXO, new Cor("azul"), Estacao.TODAS, null);
        Peca sapatoIndisponivel = new Peca(3L, "Sapato", Categoria.CALCADO, new Cor("preto"), Estacao.TODAS, null);
        sapatoIndisponivel.marcarIndisponivel();

        GeracaoLookTemplate template = new GeracaoLookPorOcasiaoTemplate(new LookEventPublisher());
        Look look = template.gerar(List.of(camisa, calca, sapatoIndisponivel), "trabalho");

        assertEquals(2, look.getPecaIds().size());
        assertFalse(look.getPecaIds().contains(sapatoIndisponivel.getId()));
    }
}
