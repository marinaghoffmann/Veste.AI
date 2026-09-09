package com.vesteai.backend.application.usecase;

import com.vesteai.backend.application.port.PecaRepository;
import com.vesteai.backend.domain.model.Peca;
import com.vesteai.backend.domain.model.vo.Categoria;
import com.vesteai.backend.domain.model.vo.Cor;
import com.vesteai.backend.domain.model.vo.Estacao;
import org.springframework.stereotype.Service;

@Service
public class RegistrarPecaUseCase {

    private final PecaRepository pecaRepository;

    public RegistrarPecaUseCase(PecaRepository pecaRepository) {
        this.pecaRepository = pecaRepository;
    }

    public Peca executar(Long donoId, String nome, Categoria categoria, Cor cor, Estacao estacao, String fotoUrl) {
        if (donoId == null) {
            throw new IllegalArgumentException("Peça precisa de um dono");
        }
        if (fotoUrl == null || fotoUrl.isBlank()) {
            throw new IllegalArgumentException("Peça precisa de uma foto");
        }

        Peca peca = new Peca(null, nome, categoria, cor, estacao, fotoUrl);
        peca.definirDono(donoId);
        return pecaRepository.salvar(peca);
    }
}
