package com.vesteai.backend.application.port;

import java.io.InputStream;

public interface ImageStorageService {

    String salvar(String nomeOriginal, InputStream conteudo);
}
