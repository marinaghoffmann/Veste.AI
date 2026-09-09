package com.vesteai.backend.domain.decorator;

import java.util.ArrayList;
import java.util.List;

public class PecaEmColecaoDecorator extends PecaDecorator {

    private final String nomeColecao;

    public PecaEmColecaoDecorator(PecaVisual interno, String nomeColecao) {
        super(interno);
        this.nomeColecao = nomeColecao;
    }

    @Override
    public List<String> getAtributosExtras() {
        List<String> atributos = new ArrayList<>(interno.getAtributosExtras());
        atributos.add("colecao:" + nomeColecao);
        return atributos;
    }
}
