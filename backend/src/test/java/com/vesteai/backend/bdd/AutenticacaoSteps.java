package com.vesteai.backend.bdd;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class AutenticacaoSteps {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private MvcResult ultimoResultado;

    @Dado("que não existe conta cadastrada com o e-mail {string}")
    public void que_nao_existe_conta_cadastrada_com_o_email(String email) {
    }

    @Dado("que existe uma conta cadastrada com e-mail {string} e senha {string}")
    public void que_existe_uma_conta_cadastrada_com_email_e_senha(String email, String senha) throws Exception {
        cadastrar("Usuário de Teste", email, senha);
    }

    @Quando("eu me cadastro com nome {string}, e-mail {string} e senha {string}")
    public void eu_me_cadastro_com_nome_email_e_senha(String nome, String email, String senha) throws Exception {
        ultimoResultado = cadastrar(nome, email, senha);
    }

    @Quando("eu faço login com e-mail {string} e senha {string}")
    public void eu_faco_login_com_email_e_senha(String email, String senha) throws Exception {
        String corpo = objectMapper.writeValueAsString(Map.of("email", email, "senha", senha));
        ultimoResultado = mockMvc.perform(
                        post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(corpo))
                .andReturn();
    }

    @Entao("o cadastro deve ser aceito")
    public void o_cadastro_deve_ser_aceito() {
        assertEquals(201, ultimoResultado.getResponse().getStatus());
    }

    @Entao("devo receber um token de acesso")
    public void devo_receber_um_token_de_acesso() throws Exception {
        assertEquals(200, ultimoResultado.getResponse().getStatus());
        String corpo = ultimoResultado.getResponse().getContentAsString();
        String token = objectMapper.readTree(corpo).get("token").asText();
        assertFalse(token.isBlank());
    }

    @Entao("o login deve ser recusado")
    public void o_login_deve_ser_recusado() {
        assertEquals(401, ultimoResultado.getResponse().getStatus());
    }

    private MvcResult cadastrar(String nome, String email, String senha) throws Exception {
        String corpo = objectMapper.writeValueAsString(Map.of("nome", nome, "email", email, "senha", senha));
        return mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON).content(corpo))
                .andReturn();
    }
}
