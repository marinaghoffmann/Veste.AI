package com.vesteai.backend.application.usecase;

import com.vesteai.backend.application.port.ImageStorageService;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class EnviarFotoPecaUseCase {

    private final ImageStorageService imageStorageService;

    public EnviarFotoPecaUseCase(ImageStorageService imageStorageService) {
        this.imageStorageService = imageStorageService;
    }

    public String executar(String nomeOriginal, InputStream conteudo) {
        return imageStorageService.salvar(nomeOriginal, conteudo);
    }
}
