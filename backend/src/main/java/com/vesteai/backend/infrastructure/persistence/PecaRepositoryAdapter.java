package com.vesteai.backend.infrastructure.persistence;

import com.vesteai.backend.application.port.PecaRepository;
import com.vesteai.backend.domain.model.Peca;
import com.vesteai.backend.domain.model.vo.Categoria;
import com.vesteai.backend.domain.model.vo.Cor;
import com.vesteai.backend.domain.model.vo.Estacao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PecaRepositoryAdapter implements PecaRepository {

    private final PecaJpaRepository jpaRepository;

    public PecaRepositoryAdapter(PecaJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Peca salvar(Peca peca) {
        PecaEntity salva = jpaRepository.save(PecaMapper.paraEntidade(peca));
        return PecaMapper.paraDominio(salva);
    }

    @Override
    public List<Peca> buscarComFiltros(Categoria categoria, Cor cor, Estacao estacao) {
        String categoriaFiltro = categoria == null ? null : categoria.name();
        String corFiltro = cor == null ? null : cor.getNome();
        String estacaoFiltro = estacao == null ? null : estacao.name();
        return jpaRepository.buscarComFiltros(categoriaFiltro, corFiltro, estacaoFiltro)
                .stream()
                .map(PecaMapper::paraDominio)
                .toList();
    }
}
