package org.rabbitmqcollector.location.infrastructure.jpa;

import java.util.List;

import org.rabbitmqcollector.location.application.port.VehicleInformationRepository;
import org.rabbitmqcollector.location.domain.VehicleStatus;
import org.rabbitmqcollector.location.infrastructure.jpa.entity.VehicleInformationEntity;
import org.rabbitmqcollector.location.infrastructure.jpa.entity.VehicleInformationJpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class VehicleInformationRepositoryAdapter implements VehicleInformationRepository {
	private final VehicleInformationJpaRepository vehicleInformationJpaRepository;

	@Override
	public VehicleInformationEntity findVehicleById(Long id) {
		return vehicleInformationJpaRepository.findById(id).orElseThrow(EntityNotFoundException::new);
	}

	@Override
	public String findVehicleIdByVehicleNumber(Long id) {
		return vehicleInformationJpaRepository.findVehicleNumberById(id).orElseThrow(EntityNotFoundException::new);
	}

	@Override
	public List<VehicleInformationEntity> findByStatus(VehicleStatus status) {
		return vehicleInformationJpaRepository.findByStatus(status);
	}
}
