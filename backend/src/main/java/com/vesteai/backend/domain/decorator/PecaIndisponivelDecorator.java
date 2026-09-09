package com.vesteai.backend.domain.decorator;

import java.util.ArrayList;
import java.util.List;

public class PecaIndisponivelDecorator extends PecaDecorator {

    public PecaIndisponivelDecorator(PecaVisual interno) {
        super(interno);
    }

    @Override
    public List<String> getAtributosExtras() {
        List<String> atributos = new ArrayList<>(interno.getAtributosExtras());
        atributos.add("indisponivel");
        return atributos;
    }
}
