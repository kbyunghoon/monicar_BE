package org.controlcenter.vehicle.infrastructure.jpa;

import java.util.Optional;

import org.controlcenter.vehicle.application.port.VehicleRepository;
import org.controlcenter.vehicle.domain.VehicleInformation;
import org.controlcenter.vehicle.infrastructure.jpa.entity.VehicleInformationEntity;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class VehicleInformationRepositoryAdapter implements VehicleRepository {
	private final VehicleInformationJpaRepository vehicleInformationJpaRepository;

	@Override
	public Optional<VehicleInformation> findById(Long vehicleId) {
		return vehicleInformationJpaRepository.findById(vehicleId)
			.map(VehicleInformationEntity::toDomain);
	}

	@Override
	public Optional<VehicleInformation> findByMdn(Long mdn) {
		return vehicleInformationJpaRepository.findByMdn(mdn)
			.map(VehicleInformationEntity::toDomain);
	}

	@Override
	public Optional<VehicleInformation> findByVehicleNumber(String vehicleNumber) {
		return vehicleInformationJpaRepository.findByVehicleNumber(vehicleNumber)
			.map(VehicleInformationEntity::toDomain);
	}

}
