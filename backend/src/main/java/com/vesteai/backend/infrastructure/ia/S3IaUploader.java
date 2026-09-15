package com.vesteai.backend.infrastructure.ia;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;

/**
 * Sobe a mesma foto que já foi salva localmente (ImageStorageService) pro
 * bucket S3 usado pela Lambda de IA. O upload no S3 é o que dispara a
 * Lambda (trigger configurado no bucket).
 *
 * "key" aqui é o caminho do objeto dentro do bucket (ex: pecas/1.png) —
 * é o mesmo valor que depois volta no campo peca_key do resultado da IA,
 * e é o que o frontend usa pra fazer o polling.
 */
@Service
public class S3IaUploader {

    private final S3Client s3Client;
    private final String bucketName;

    public S3IaUploader(
            S3Client s3Client,
            @Value("${app.ia.s3-bucket:guarda-roupa}") String bucketName
    ) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
    }

    /**
     * @param nomeArquivo nome do arquivo (ex: 1.png) — vira "pecas/1.png"
     *                    no bucket, no mesmo padrão usado nos testes da Lambda.
     * @return a key usada no S3 (ex: "pecas/1.png"), pra guardar e usar no
     *         polling do resultado da IA.
     */
    public String enviarParaAnaliseDeIa(InputStream conteudo, long tamanhoBytes, String nomeArquivo, String contentType)
            throws IOException {
        String key = "pecas/" + nomeArquivo;

        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .contentType(contentType)
                        .build(),
                RequestBody.fromInputStream(conteudo, tamanhoBytes)
        );

        return key;
    }
}
