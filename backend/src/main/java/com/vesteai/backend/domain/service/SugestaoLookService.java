package com.vesteai.backend.domain.service;

import com.vesteai.backend.domain.model.Peca;
import com.vesteai.backend.domain.model.SugestaoLook;
import com.vesteai.backend.domain.strategy.SugestaoLookStrategy;

import java.util.List;

public class SugestaoLookService {

    private final SugestaoLookStrategy strategy;

    public SugestaoLookService(SugestaoLookStrategy strategy) {
        this.strategy = strategy;
    }

    public SugestaoLook gerarSugestao(List<Peca> pecasDisponiveis, String ocasiao) {
        return strategy.sugerir(pecasDisponiveis, ocasiao);
    }
}
