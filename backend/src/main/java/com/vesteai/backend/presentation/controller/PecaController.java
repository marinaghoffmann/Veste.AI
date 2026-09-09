package com.vesteai.backend.presentation.controller;

import com.vesteai.backend.application.usecase.FiltrarPecasUseCase;
import com.vesteai.backend.domain.model.vo.Categoria;
import com.vesteai.backend.domain.model.vo.Cor;
import com.vesteai.backend.domain.model.vo.Estacao;
import com.vesteai.backend.presentation.dto.PecaResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/pecas")
public class PecaController {

    private final FiltrarPecasUseCase filtrarPecasUseCase;

    public PecaController(FiltrarPecasUseCase filtrarPecasUseCase) {
        this.filtrarPecasUseCase = filtrarPecasUseCase;
    }

    @GetMapping
    public List<PecaResponse> filtrar(
            @RequestParam(required = false) Categoria categoria,
            @RequestParam(required = false) String cor,
            @RequestParam(required = false) Estacao estacao) {
        Cor corFiltro = cor == null ? null : new Cor(cor);
        return filtrarPecasUseCase.executar(categoria, corFiltro, estacao)
                .stream()
                .map(PecaResponse::de)
                .toList();
    }
}
