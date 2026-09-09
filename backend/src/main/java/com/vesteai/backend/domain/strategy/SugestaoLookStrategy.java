package com.vesteai.backend.domain.strategy;

import com.vesteai.backend.domain.model.Peca;
import com.vesteai.backend.domain.model.SugestaoLook;

import java.util.List;

public interface SugestaoLookStrategy {

    SugestaoLook sugerir(List<Peca> pecasDisponiveis, String ocasiao);
}
