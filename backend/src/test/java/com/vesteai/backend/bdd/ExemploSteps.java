package com.vesteai.backend.bdd;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.junit.jupiter.api.Assertions;

public class ExemploSteps {

    private boolean ambienteConfigurado;
    private boolean testesExecutados;

    @Dado("que o ambiente do projeto está configurado")
    public void que_o_ambiente_do_projeto_esta_configurado() {
        ambienteConfigurado = true;
    }

    @Quando("os testes automatizados forem executados")
    public void os_testes_automatizados_forem_executados() {
        testesExecutados = ambienteConfigurado;
    }

    @Entao("o Cucumber deve responder com sucesso")
    public void o_cucumber_deve_responder_com_sucesso() {
        Assertions.assertTrue(testesExecutados);
    }
}
