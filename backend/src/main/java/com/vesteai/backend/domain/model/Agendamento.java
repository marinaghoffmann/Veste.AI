package com.vesteai.backend.domain.model;

import java.time.LocalDate;

public class Agendamento {

    private final Long id;
    private final Long lookId;
    private final LocalDate data;

    public Agendamento(Long id, Long lookId, LocalDate data) {
        if (lookId == null) {
            throw new IllegalArgumentException("Agendamento precisa de um look");
        }
        if (data == null) {
            throw new IllegalArgumentException("Agendamento precisa de uma data");
        }
        this.id = id;
        this.lookId = lookId;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public Long getLookId() {
        return lookId;
    }

    public LocalDate getData() {
        return data;
    }
}
