package com.vesteai.backend.infrastructure.storage;

import com.vesteai.backend.application.port.ImageStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class LocalImageStorageService implements ImageStorageService {

    private final Path diretorioBase;

    public LocalImageStorageService(@Value("${app.storage.pecas-dir:./uploads/pecas}") String diretorioBase) {
        this.diretorioBase = Path.of(diretorioBase);
        try {
            Files.createDirectories(this.diretorioBase);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public String salvar(String nomeOriginal, InputStream conteudo) {
        String extensao = extrairExtensao(nomeOriginal);
        String nomeArquivo = UUID.randomUUID() + extensao;
        Path destino = diretorioBase.resolve(nomeArquivo);
        try {
            Files.copy(conteudo, destino);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return "/uploads/pecas/" + nomeArquivo;
    }

    private String extrairExtensao(String nomeOriginal) {
        int ponto = nomeOriginal == null ? -1 : nomeOriginal.lastIndexOf('.');
        return ponto >= 0 ? nomeOriginal.substring(ponto) : "";
    }
}
