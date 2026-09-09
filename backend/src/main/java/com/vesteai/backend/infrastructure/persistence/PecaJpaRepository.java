package com.vesteai.backend.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PecaJpaRepository extends JpaRepository<PecaEntity, Long> {

    @Query("""
            SELECT p FROM PecaEntity p
            WHERE (:categoria IS NULL OR p.categoria = :categoria)
              AND (:cor IS NULL OR p.cor = :cor)
              AND (:estacao IS NULL OR p.estacao = :estacao)
            """)
    List<PecaEntity> buscarComFiltros(
            @Param("categoria") String categoria,
            @Param("cor") String cor,
            @Param("estacao") String estacao);
}
