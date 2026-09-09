package com.vesteai.backend.bdd;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class CadastroPecaSteps {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String token;
    private String fotoUrl;
    private MvcResult ultimoResultado;

    @Dado("que estou autenticado como {string}")
    public void que_estou_autenticado_como(String email) throws Exception {
        String corpoRegistro = objectMapper.writeValueAsString(
                Map.of("nome", "Usuário BDD", "email", email, "senha", "senhaSegura123"));
        mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON).content(corpoRegistro));

        String corpoLogin = objectMapper.writeValueAsString(Map.of("email", email, "senha", "senhaSegura123"));
        String resposta = mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(corpoLogin))
                .andReturn()
                .getResponse()
                .getContentAsString();
        token = objectMapper.readTree(resposta).get("token").asText();
    }

    @E("que enviei a foto {string} da peça")
    public void que_enviei_a_foto_da_peca(String nomeArquivo) throws Exception {
        MockMultipartFile arquivo = new MockMultipartFile("arquivo", nomeArquivo, "image/png", new byte[] {1, 2, 3});
        String resposta = mockMvc.perform(multipart("/api/pecas/fotos").file(arquivo)
                        .header("Authorization", "Bearer " + token))
                .andReturn()
                .getResponse()
                .getContentAsString();
        fotoUrl = objectMapper.readTree(resposta).get("fotoUrl").asText();
    }

    @Quando("eu cadastro uma peça com nome {string}, categoria {string}, cor {string} e estação {string}")
    public void eu_cadastro_uma_peca_com_nome_categoria_cor_e_estacao(
            String nome, String categoria, String cor, String estacao) throws Exception {
        cadastrarPeca(nome, categoria, cor, estacao, fotoUrl);
    }

    @Quando(
            "eu tento cadastrar uma peça sem foto com nome {string}, categoria {string}, cor {string} e estação {string}")
    public void eu_tento_cadastrar_uma_peca_sem_foto(String nome, String categoria, String cor, String estacao)
            throws Exception {
        cadastrarPeca(nome, categoria, cor, estacao, null);
    }

    private void cadastrarPeca(String nome, String categoria, String cor, String estacao, String fotoUrl)
            throws Exception {
        Map<String, String> corpoMap = new HashMap<>();
        corpoMap.put("nome", nome);
        corpoMap.put("categoria", categoria);
        corpoMap.put("cor", cor);
        corpoMap.put("estacao", estacao);
        corpoMap.put("fotoUrl", fotoUrl);
        String corpo = objectMapper.writeValueAsString(corpoMap);

        ultimoResultado = mockMvc.perform(post("/api/pecas")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(corpo))
                .andReturn();
    }

    @Entao("a peça deve ser cadastrada com sucesso")
    public void a_peca_deve_ser_cadastrada_com_sucesso() {
        assertEquals(201, ultimoResultado.getResponse().getStatus());
    }

    @Entao("o cadastro deve ser recusado")
    public void o_cadastro_deve_ser_recusado() {
        assertEquals(400, ultimoResultado.getResponse().getStatus());
    }
}
