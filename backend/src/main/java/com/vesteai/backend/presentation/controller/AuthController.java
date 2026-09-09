package com.vesteai.backend.presentation.controller;

import com.vesteai.backend.application.usecase.AutenticarUsuarioUseCase;
import com.vesteai.backend.application.usecase.RegistrarUsuarioUseCase;
import com.vesteai.backend.presentation.dto.LoginRequest;
import com.vesteai.backend.presentation.dto.RegistroRequest;
import com.vesteai.backend.presentation.dto.TokenResponse;
import com.vesteai.backend.presentation.dto.UsuarioResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final RegistrarUsuarioUseCase registrarUsuarioUseCase;
    private final AutenticarUsuarioUseCase autenticarUsuarioUseCase;

    public AuthController(
            RegistrarUsuarioUseCase registrarUsuarioUseCase, AutenticarUsuarioUseCase autenticarUsuarioUseCase) {
        this.registrarUsuarioUseCase = registrarUsuarioUseCase;
        this.autenticarUsuarioUseCase = autenticarUsuarioUseCase;
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioResponse> registrar(@Valid @RequestBody RegistroRequest request) {
        var usuario = registrarUsuarioUseCase.executar(request.nome(), request.email(), request.senha());
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioResponse.de(usuario));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest request) {
        String token = autenticarUsuarioUseCase.executar(request.email(), request.senha());
        return ResponseEntity.ok(TokenResponse.de(token));
    }
}
