package com.vesteai.backend.application.usecase;

import com.vesteai.backend.application.port.PecaRepository;
import com.vesteai.backend.domain.model.Peca;
import com.vesteai.backend.domain.model.vo.Categoria;
import com.vesteai.backend.domain.model.vo.Cor;
import com.vesteai.backend.domain.model.vo.Estacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RegistrarPecaUseCaseTest {

    private final List<Peca> pecasSalvas = new ArrayList<>();

    private final PecaRepository pecaRepository = new PecaRepository() {
        @Override
        public Peca salvar(Peca peca) {
            Peca salva = new Peca(
                    (long) (pecasSalvas.size() + 1),
                    peca.getNome(),
                    peca.getCategoria(),
                    peca.getCor(),
                    peca.getEstacao(),
                    peca.getFotoUrl());
            salva.definirDono(peca.getDonoId());
            pecasSalvas.add(salva);
            return salva;
        }

        @Override
        public List<Peca> buscarComFiltros(Categoria categoria, Cor cor, Estacao estacao) {
            return pecasSalvas;
        }
    };

    private RegistrarPecaUseCase useCase;

    @BeforeEach
    void setUp() {
        pecasSalvas.clear();
        useCase = new RegistrarPecaUseCase(pecaRepository);
    }

    @Test
    void cadastraPecaAssociadaAoDono() {
        Peca peca = useCase.executar(
                1L, "Camisa social", Categoria.PARTE_DE_CIMA, new Cor("branco"), Estacao.TODAS, "/uploads/pecas/foto.png");

        assertEquals(1L, peca.getDonoId());
        assertEquals("Camisa social", peca.getNome());
        assertEquals("/uploads/pecas/foto.png", peca.getFotoUrl());
    }

    @Test
    void rejeitaCadastroSemDono() {
        assertThrows(IllegalArgumentException.class,
                () -> useCase.executar(null, "Camisa social", Categoria.PARTE_DE_CIMA, new Cor("branco"), Estacao.TODAS,
                        "/uploads/pecas/foto.png"));
    }

    @Test
    void rejeitaCadastroSemFoto() {
        assertThrows(IllegalArgumentException.class,
                () -> useCase.executar(1L, "Camisa social", Categoria.PARTE_DE_CIMA, new Cor("branco"), Estacao.TODAS, null));
    }
}
