package com.vesteai.backend.application.port;

public interface PasswordHasher {

    String hash(String senhaBruta);

    boolean confere(String senhaBruta, String senhaHash);
}
