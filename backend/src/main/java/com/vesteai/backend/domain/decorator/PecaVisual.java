package com.vesteai.backend.domain.decorator;

import com.vesteai.backend.domain.model.Peca;

import java.util.List;

public interface PecaVisual {

    Peca getPeca();

    List<String> getAtributosExtras();
}
