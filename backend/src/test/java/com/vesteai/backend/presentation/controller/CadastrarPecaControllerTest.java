package com.vesteai.backend.presentation.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CadastrarPecaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String autenticarNovoUsuario(String email) throws Exception {
        String corpoRegistro = objectMapper.writeValueAsString(
                new CorpoRegistro("Dono das Peças", email, "senhaSegura123"));
        mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON).content(corpoRegistro))
                .andExpect(status().isCreated());

        String corpoLogin = objectMapper.writeValueAsString(new CorpoLogin(email, "senhaSegura123"));
        String respostaLogin = mockMvc.perform(
                        post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(corpoLogin))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode json = objectMapper.readTree(respostaLogin);
        return json.get("token").asText();
    }

    @Test
    void enviaFotoECadastraPecaComSucesso() throws Exception {
        String token = autenticarNovoUsuario("dona.pecas@example.com");

        MockMultipartFile arquivo = new MockMultipartFile("arquivo", "camisa.png", "image/png", new byte[] {1, 2, 3});
        String respostaFoto = mockMvc.perform(multipart("/api/pecas/fotos").file(arquivo)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String fotoUrl = objectMapper.readTree(respostaFoto).get("fotoUrl").asText();

        String corpoPeca = objectMapper.writeValueAsString(
                new CorpoPeca("Camisa social", "PARTE_DE_CIMA", "branco", "TODAS", fotoUrl));

        mockMvc.perform(post("/api/pecas")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(corpoPeca))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Camisa social"))
                .andExpect(jsonPath("$.categoria").value("PARTE_DE_CIMA"))
                .andExpect(jsonPath("$.cor").value("branco"))
                .andExpect(jsonPath("$.fotoUrl").value(fotoUrl))
                .andExpect(jsonPath("$.disponivel").value(true));
    }

    @Test
    void rejeitaCadastroSemAutenticacao() throws Exception {
        String corpoPeca = objectMapper.writeValueAsString(
                new CorpoPeca("Camisa social", "PARTE_DE_CIMA", "branco", "TODAS", "/uploads/pecas/foto.png"));

        mockMvc.perform(post("/api/pecas").contentType(MediaType.APPLICATION_JSON).content(corpoPeca))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void rejeitaCadastroComCamposObrigatoriosFaltando() throws Exception {
        String token = autenticarNovoUsuario("dona.pecas.invalidas@example.com");

        String corpoPeca = objectMapper.writeValueAsString(new CorpoPeca("", null, "", null, ""));

        mockMvc.perform(post("/api/pecas")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(corpoPeca))
                .andExpect(status().isBadRequest());
    }

    private record CorpoRegistro(String nome, String email, String senha) {
    }

    private record CorpoLogin(String email, String senha) {
    }

    private record CorpoPeca(String nome, String categoria, String cor, String estacao, String fotoUrl) {
    }
}
