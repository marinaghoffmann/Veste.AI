package com.vesteai.backend.infrastructure.notification;

import com.vesteai.backend.domain.event.LookEventListener;
import com.vesteai.backend.domain.model.Look;
import com.vesteai.backend.domain.model.Peca;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class LogLookEventListener implements LookEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogLookEventListener.class);

    @Override
    public void aoCriarLook(Look look) {
        LOGGER.info("Look criado: {}", look.getNome());
    }

    @Override
    public void aoFavoritarLook(Look look) {
        LOGGER.info("Look favoritado: {}", look.getNome());
    }

    @Override
    public void aoAgendarLook(Look look, LocalDate data) {
        LOGGER.info("Look '{}' agendado para {}", look.getNome(), data);
    }

    @Override
    public void aoPecaFicarIndisponivel(Peca peca) {
        LOGGER.info("Peça indisponível: {}", peca.getNome());
    }
}
