package com.vesteai.backend.domain.event;

import com.vesteai.backend.domain.model.Look;
import com.vesteai.backend.domain.model.Peca;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LookEventPublisher {

    private final List<LookEventListener> listeners = new ArrayList<>();

    public void registrar(LookEventListener listener) {
        listeners.add(listener);
    }

    public void notificarLookCriado(Look look) {
        listeners.forEach(listener -> listener.aoCriarLook(look));
    }

    public void notificarLookFavoritado(Look look) {
        listeners.forEach(listener -> listener.aoFavoritarLook(look));
    }

    public void notificarLookAgendado(Look look, LocalDate data) {
        listeners.forEach(listener -> listener.aoAgendarLook(look, data));
    }

    public void notificarPecaIndisponivel(Peca peca) {
        listeners.forEach(listener -> listener.aoPecaFicarIndisponivel(peca));
    }
}
