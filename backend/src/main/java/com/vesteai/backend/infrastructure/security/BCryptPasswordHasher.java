package com.vesteai.backend.infrastructure.security;

import com.vesteai.backend.application.port.PasswordHasher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordHasher implements PasswordHasher {

    private final PasswordEncoder passwordEncoder;

    public BCryptPasswordHasher(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String hash(String senhaBruta) {
        return passwordEncoder.encode(senhaBruta);
    }

    @Override
    public boolean confere(String senhaBruta, String senhaHash) {
        return passwordEncoder.matches(senhaBruta, senhaHash);
    }
}
