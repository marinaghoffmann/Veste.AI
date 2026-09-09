package com.vesteai.backend.domain.event;

import com.vesteai.backend.domain.model.Look;
import com.vesteai.backend.domain.model.Peca;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LookEventPublisherTest {

    @Test
    void notificaTodosOsObservadoresRegistrados() {
        LookEventPublisher publisher = new LookEventPublisher();
        AtomicBoolean notificado = new AtomicBoolean(false);
        publisher.registrar(new LookEventListener() {
            @Override
            public void aoCriarLook(Look look) {
                notificado.set(true);
            }

            @Override
            public void aoFavoritarLook(Look look) {
            }

            @Override
            public void aoAgendarLook(Look look, LocalDate data) {
            }

            @Override
            public void aoPecaFicarIndisponivel(Peca peca) {
            }
        });

        Look look = new Look(1L, "Look casual", Set.of(1L, 2L));
        publisher.notificarLookCriado(look);

        assertTrue(notificado.get());
    }
}
