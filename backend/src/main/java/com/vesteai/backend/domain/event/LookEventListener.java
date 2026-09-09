package com.vesteai.backend.domain.event;

import com.vesteai.backend.domain.model.Look;
import com.vesteai.backend.domain.model.Peca;

import java.time.LocalDate;

public interface LookEventListener {

    void aoCriarLook(Look look);

    void aoFavoritarLook(Look look);

    void aoAgendarLook(Look look, LocalDate data);

    void aoPecaFicarIndisponivel(Peca peca);
}
