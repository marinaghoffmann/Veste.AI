package com.vesteai.backend.presentation.controller;

import com.vesteai.backend.application.port.PecaRepository;
import com.vesteai.backend.domain.model.Peca;
import com.vesteai.backend.domain.model.vo.Categoria;
import com.vesteai.backend.domain.model.vo.Cor;
import com.vesteai.backend.domain.model.vo.Estacao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PecaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PecaRepository pecaRepository;

    @Test
    void filtraPecasCadastradasPorCategoria() throws Exception {
        pecaRepository.salvar(
                new Peca(null, "Camisa social", Categoria.PARTE_DE_CIMA, new Cor("bege-teste-filtro"), Estacao.TODAS, null));
        pecaRepository.salvar(
                new Peca(null, "Calça jeans", Categoria.PARTE_DE_BAIXO, new Cor("azul-teste-filtro"), Estacao.TODAS, null));

        mockMvc.perform(get("/api/pecas").param("categoria", "PARTE_DE_CIMA").param("cor", "bege-teste-filtro"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nome").value("Camisa social"));
    }
}
