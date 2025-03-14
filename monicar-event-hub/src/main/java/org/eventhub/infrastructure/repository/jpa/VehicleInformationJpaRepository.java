package org.eventhub.infrastructure.repository.jpa;

import java.util.Optional;

import org.eventhub.infrastructure.repository.jpa.entity.VehicleInformationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleInformationJpaRepository extends JpaRepository<VehicleInformationEntity, Long> {
	Optional<VehicleInformationEntity> findByMdn(Long mdn);
}
