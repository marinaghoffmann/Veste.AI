package com.vesteai.backend.application.usecase;

import com.vesteai.backend.application.port.PecaRepository;
import com.vesteai.backend.domain.model.Peca;
import com.vesteai.backend.domain.model.vo.Categoria;
import com.vesteai.backend.domain.model.vo.Cor;
import com.vesteai.backend.domain.model.vo.Estacao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FiltrarPecasUseCase {

    private final PecaRepository pecaRepository;

    public FiltrarPecasUseCase(PecaRepository pecaRepository) {
        this.pecaRepository = pecaRepository;
    }

    public List<Peca> executar(Categoria categoria, Cor cor, Estacao estacao) {
        return pecaRepository.buscarComFiltros(categoria, cor, estacao);
    }
}
