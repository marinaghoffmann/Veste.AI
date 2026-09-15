package com.vesteai.backend.presentation.controller;

import com.vesteai.backend.application.usecase.EnviarFotoPecaUseCase;
import com.vesteai.backend.application.usecase.FiltrarPecasUseCase;
import com.vesteai.backend.application.usecase.RegistrarPecaUseCase;
import com.vesteai.backend.domain.model.vo.Categoria;
import com.vesteai.backend.domain.model.vo.Cor;
import com.vesteai.backend.domain.model.vo.Estacao;
import com.vesteai.backend.infrastructure.ia.S3IaUploader;
import com.vesteai.backend.infrastructure.security.UsuarioAutenticado;
import com.vesteai.backend.presentation.dto.FotoResponse;
import com.vesteai.backend.presentation.dto.PecaRequest;
import com.vesteai.backend.presentation.dto.PecaResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;

@RestController
@RequestMapping("/api/pecas")
public class PecaController {

    private static final Logger log = LoggerFactory.getLogger(PecaController.class);

    private final FiltrarPecasUseCase filtrarPecasUseCase;
    private final RegistrarPecaUseCase registrarPecaUseCase;
    private final EnviarFotoPecaUseCase enviarFotoPecaUseCase;
    private final S3IaUploader s3IaUploader;

    public PecaController(
            FiltrarPecasUseCase filtrarPecasUseCase,
            RegistrarPecaUseCase registrarPecaUseCase,
            EnviarFotoPecaUseCase enviarFotoPecaUseCase,
            S3IaUploader s3IaUploader) {
        this.filtrarPecasUseCase = filtrarPecasUseCase;
        this.registrarPecaUseCase = registrarPecaUseCase;
        this.enviarFotoPecaUseCase = enviarFotoPecaUseCase;
        this.s3IaUploader = s3IaUploader;
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
            // Lê o conteúdo uma vez só, porque MultipartFile.getInputStream()
            // pode ser consumido apenas uma vez de forma confiável — e
            // precisamos usar os bytes duas vezes (salvar local + subir pro S3).
            byte[] conteudo = arquivo.getBytes();

            String fotoUrl = enviarFotoPecaUseCase.executar(
                    arquivo.getOriginalFilename(), new ByteArrayInputStream(conteudo));

            String iaKey = enviarParaAnaliseDeIaSemQuebrarOFluxo(arquivo, conteudo);

            return ResponseEntity.ok(new FotoResponse(fotoUrl, iaKey));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Sobe a foto pro S3 (dispara a Lambda de IA). Se isso falhar por
     * qualquer motivo (S3 fora do ar, sem permissão, sem internet etc.),
     * NÃO deve impedir o cadastro da peça — o usuário sempre pode
     * preencher categoria/cor manualmente. Por isso o erro é só logado,
     * e o método devolve null nesse caso.
     */
    private String enviarParaAnaliseDeIaSemQuebrarOFluxo(MultipartFile arquivo, byte[] conteudo) {
        try {
            return s3IaUploader.enviarParaAnaliseDeIa(
                    new ByteArrayInputStream(conteudo),
                    conteudo.length,
                    arquivo.getOriginalFilename(),
                    arquivo.getContentType());
        } catch (Exception e) {
            log.warn("Não foi possível enviar a foto para análise de IA (seguindo sem sugestão automática)", e);
            return null;
        }
    }
}
