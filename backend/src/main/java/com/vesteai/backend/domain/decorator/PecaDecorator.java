package com.vesteai.backend.domain.decorator;

import com.vesteai.backend.domain.model.Peca;

public abstract class PecaDecorator implements PecaVisual {

    protected final PecaVisual interno;

    protected PecaDecorator(PecaVisual interno) {
        this.interno = interno;
    }

    @Override
    public Peca getPeca() {
        return interno.getPeca();
    }
}
