package org.controlcenter.company.infrastructure.jpa;

import java.util.Optional;

import org.controlcenter.company.infrastructure.jpa.entity.ManagerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerJpaRepository extends JpaRepository<ManagerEntity, String> {
	Optional<ManagerEntity> findByLoginId(String loginId);
}
