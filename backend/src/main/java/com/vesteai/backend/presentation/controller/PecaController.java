package com.vesteai.backend.presentation.controller;

import com.vesteai.backend.application.usecase.EnviarFotoPecaUseCase;
import com.vesteai.backend.application.usecase.FiltrarPecasUseCase;
import com.vesteai.backend.application.usecase.RegistrarPecaUseCase;
import com.vesteai.backend.domain.model.vo.Categoria;
import com.vesteai.backend.domain.model.vo.Cor;
import com.vesteai.backend.domain.model.vo.Estacao;
import com.vesteai.backend.infrastructure.security.UsuarioAutenticado;
import com.vesteai.backend.presentation.dto.FotoResponse;
import com.vesteai.backend.presentation.dto.PecaRequest;
import com.vesteai.backend.presentation.dto.PecaResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;

@RestController
@RequestMapping("/api/pecas")
public class PecaController {

    private final FiltrarPecasUseCase filtrarPecasUseCase;
    private final RegistrarPecaUseCase registrarPecaUseCase;
    private final EnviarFotoPecaUseCase enviarFotoPecaUseCase;

    public PecaController(
            FiltrarPecasUseCase filtrarPecasUseCase,
            RegistrarPecaUseCase registrarPecaUseCase,
            EnviarFotoPecaUseCase enviarFotoPecaUseCase) {
        this.filtrarPecasUseCase = filtrarPecasUseCase;
        this.registrarPecaUseCase = registrarPecaUseCase;
        this.enviarFotoPecaUseCase = enviarFotoPecaUseCase;
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

    @PostMapping
    public ResponseEntity<PecaResponse> cadastrar(@Valid @RequestBody PecaRequest request, Authentication authentication) {
        UsuarioAutenticado usuario = (UsuarioAutenticado) authentication.getPrincipal();
        var peca = registrarPecaUseCase.executar(
                usuario.id(), request.nome(), request.categoria(), new Cor(request.cor()), request.estacao(), request.fotoUrl());
        return ResponseEntity.status(HttpStatus.CREATED).body(PecaResponse.de(peca));
    }

    @PostMapping("/fotos")
    public ResponseEntity<FotoResponse> enviarFoto(@RequestParam("arquivo") MultipartFile arquivo) {
        try {
            String fotoUrl = enviarFotoPecaUseCase.executar(arquivo.getOriginalFilename(), arquivo.getInputStream());
            return ResponseEntity.ok(new FotoResponse(fotoUrl));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
