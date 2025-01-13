package org.controlcenter.vehicle.infrastructure.jpa;

import org.controlcenter.vehicle.infrastructure.jpa.entity.VehicleInformationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehicleInformationJpaRepository extends JpaRepository<VehicleInformationEntity, Long> {
	Optional<VehicleInformationEntity> findByMdn(Long mdn);

}
