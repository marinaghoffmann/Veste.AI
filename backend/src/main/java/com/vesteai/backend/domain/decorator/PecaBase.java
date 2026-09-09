package com.vesteai.backend.domain.decorator;

import com.vesteai.backend.domain.model.Peca;

import java.util.List;

public class PecaBase implements PecaVisual {

    private final Peca peca;

    public PecaBase(Peca peca) {
        this.peca = peca;
    }

    @Override
    public Peca getPeca() {
        return peca;
    }

    @Override
    public List<String> getAtributosExtras() {
        return List.of();
    }
}
