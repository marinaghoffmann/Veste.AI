package com.vesteai.backend.domain.model;

import java.util.Set;

public class SugestaoLook {

    private final Long id;
    private final String ocasiao;
    private final String justificativaIA;
    private final Set<Long> pecaIds;

    public SugestaoLook(Long id, String ocasiao, String justificativaIA, Set<Long> pecaIds) {
        if (pecaIds == null || pecaIds.size() < 2) {
            throw new IllegalArgumentException("Uma sugestão precisa de ao menos duas peças");
        }
        this.id = id;
        this.ocasiao = ocasiao;
        this.justificativaIA = justificativaIA;
        this.pecaIds = Set.copyOf(pecaIds);
    }

    public Long getId() {
        return id;
    }

    public String getOcasiao() {
        return ocasiao;
    }

    public String getJustificativaIA() {
        return justificativaIA;
    }

    public Set<Long> getPecaIds() {
        return pecaIds;
    }
}
