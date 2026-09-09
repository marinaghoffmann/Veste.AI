package com.vesteai.backend.application.port;

import com.vesteai.backend.domain.model.Peca;
import com.vesteai.backend.domain.model.vo.Categoria;
import com.vesteai.backend.domain.model.vo.Cor;
import com.vesteai.backend.domain.model.vo.Estacao;

import java.util.List;

public interface PecaRepository {

    Peca salvar(Peca peca);

    List<Peca> buscarComFiltros(Categoria categoria, Cor cor, Estacao estacao);
}
