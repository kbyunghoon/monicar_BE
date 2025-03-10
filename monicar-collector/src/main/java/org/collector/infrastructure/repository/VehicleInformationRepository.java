package org.collector.infrastructure.repository;

import org.collector.domain.VehicleInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehicleInformationRepository extends JpaRepository<VehicleInformation, Long> {
	Optional<VehicleInformation> findByMdn(Long mdn);
}
