package org.rabbitmqcollector.location.infrastructure.jpa;

import java.util.Optional;

import org.rabbitmqcollector.location.infrastructure.jpa.entity.CycleInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CycleInfoJpaRepository extends JpaRepository<CycleInfoEntity, Long> {
	Optional<CycleInfoEntity> findTopByVehicleIdOrderByCreatedAtDesc(Long vehicleId);
}
