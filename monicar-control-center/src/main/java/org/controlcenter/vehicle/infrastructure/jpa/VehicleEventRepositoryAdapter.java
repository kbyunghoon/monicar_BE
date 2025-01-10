package org.controlcenter.vehicle.infrastructure.jpa;

import lombok.RequiredArgsConstructor;

import org.controlcenter.vehicle.application.port.VehicleEventRepository;
import org.controlcenter.vehicle.domain.VehicleEvent;
import org.controlcenter.vehicle.infrastructure.jpa.entity.VehicleEventEntity;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class VehicleEventRepositoryAdapter implements VehicleEventRepository {
	private final VehicleEventJpaRepository vehicleEventJpaRepository;

	@Override
	public VehicleEvent save(VehicleEvent vehicleEvent) {
		return vehicleEventJpaRepository.save(VehicleEventEntity.from(vehicleEvent)).toDomain();
	}
}
