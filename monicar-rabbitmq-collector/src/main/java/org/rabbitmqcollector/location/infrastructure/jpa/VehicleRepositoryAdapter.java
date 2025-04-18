package org.rabbitmqcollector.location.infrastructure.jpa;

import org.rabbitmqcollector.location.application.port.VehicleRepository;
import org.rabbitmqcollector.location.infrastructure.jpa.entity.VehicleInformationEntity;
import org.rabbitmqcollector.location.infrastructure.jpa.entity.VehicleJpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class VehicleRepositoryAdapter implements VehicleRepository {
	private final VehicleJpaRepository vehicleJpaRepository;

	@Override
	public VehicleInformationEntity findVehicleById(Long id) {
		return vehicleJpaRepository.findById(id).orElseThrow(EntityNotFoundException::new);
	}

	@Override
	public String findVehicleIdByVehicleNumber(Long id) {
		return vehicleJpaRepository.findVehicleNumberById(id).orElseThrow(EntityNotFoundException::new);
	}
}
