package org.controlcenter.vehicle.infrastructure.jpa;

import java.util.List;

import org.controlcenter.vehicle.application.port.VehicleTypeRepository;
import org.controlcenter.vehicle.domain.VehicleType;
import org.controlcenter.vehicle.infrastructure.jpa.entity.VehicleTypeEntity;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class VehicleTypeRepositoryAdapter implements VehicleTypeRepository {
	private final VehicleTypesJpaRepository vehicleTypesJpaRepository;

	@Override
	public List<VehicleType> findAll() {
		return vehicleTypesJpaRepository.findAll()
			.stream()
			.map(VehicleTypeEntity::toDomain)
			.toList();
	}
}
