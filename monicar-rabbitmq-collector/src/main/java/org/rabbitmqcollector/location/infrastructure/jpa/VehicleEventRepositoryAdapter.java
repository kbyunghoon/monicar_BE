package org.rabbitmqcollector.location.infrastructure.jpa;

import org.rabbitmqcollector.location.application.port.VehicleEventRepository;
import org.rabbitmqcollector.location.domain.VehicleEvent;
import org.rabbitmqcollector.location.infrastructure.jpa.entity.VehicleEventEntity;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class VehicleEventRepositoryAdapter implements VehicleEventRepository {
	private final VehicleEventJpaRepository vehicleEventJpaRepository;

	@Override
	public VehicleEvent save(VehicleEvent vehicleEvent) {
		return vehicleEventJpaRepository.save(VehicleEventEntity.from(vehicleEvent)).toDomain();
	}
}
