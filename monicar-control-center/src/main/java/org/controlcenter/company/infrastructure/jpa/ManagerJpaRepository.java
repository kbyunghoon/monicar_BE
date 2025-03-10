package org.controlcenter.company.infrastructure.jpa;

import java.util.Optional;

import org.controlcenter.company.domain.Manager;
import org.controlcenter.company.infrastructure.jpa.entity.ManagerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerJpaRepository extends JpaRepository<ManagerEntity, String> {
	Manager save(Manager manager);
	Optional<ManagerEntity> findByLoginId(String loginId);
	Optional<ManagerEntity> findByEmail(String email);
}
