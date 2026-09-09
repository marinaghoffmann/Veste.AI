package com.vesteai.backend.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LookJpaRepository extends JpaRepository<LookEntity, Long> {
}
