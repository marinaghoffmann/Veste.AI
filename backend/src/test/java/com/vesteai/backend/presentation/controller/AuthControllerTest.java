package com.vesteai.backend.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void registraEAutenticaComSucesso() throws Exception {
        String corpoRegistro = objectMapper.writeValueAsString(
                new CorpoRegistro("Ana Souza", "ana.souza@example.com", "senhaSegura123"));

        mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON).content(corpoRegistro))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Ana Souza"))
                .andExpect(jsonPath("$.email").value("ana.souza@example.com"))
                .andExpect(jsonPath("$.senha").doesNotExist());

        String corpoLogin = objectMapper.writeValueAsString(
                new CorpoLogin("ana.souza@example.com", "senhaSegura123"));

        mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(corpoLogin))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andExpect(jsonPath("$.tipo").value("Bearer"));
    }

    @Test
    void rejeitaCadastroComEmailDuplicado() throws Exception {
        String corpoRegistro = objectMapper.writeValueAsString(
                new CorpoRegistro("Bruno Lima", "bruno.lima@example.com", "senhaSegura123"));

        mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON).content(corpoRegistro))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON).content(corpoRegistro))
                .andExpect(status().isConflict());
    }

    @Test
    void rejeitaLoginComSenhaIncorreta() throws Exception {
        String corpoRegistro = objectMapper.writeValueAsString(
                new CorpoRegistro("Carla Dias", "carla.dias@example.com", "senhaSegura123"));
        mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON).content(corpoRegistro))
                .andExpect(status().isCreated());

        String corpoLogin = objectMapper.writeValueAsString(
                new CorpoLogin("carla.dias@example.com", "senhaErrada"));

        mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(corpoLogin))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void rejeitaCadastroComSenhaCurta() throws Exception {
        String corpoRegistro = objectMapper.writeValueAsString(
                new CorpoRegistro("Duda Reis", "duda.reis@example.com", "123"));

        mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON).content(corpoRegistro))
                .andExpect(status().isBadRequest());
    }

    private record CorpoRegistro(String nome, String email, String senha) {
    }

    private record CorpoLogin(String email, String senha) {
    }
}
