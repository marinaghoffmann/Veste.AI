package com.vesteai.backend.domain.decorator;

import java.util.ArrayList;
import java.util.List;

public class PecaFavoritaDecorator extends PecaDecorator {

    public PecaFavoritaDecorator(PecaVisual interno) {
        super(interno);
    }

    @Override
    public List<String> getAtributosExtras() {
        List<String> atributos = new ArrayList<>(interno.getAtributosExtras());
        atributos.add("favorito");
        return atributos;
    }
}
