package org.rabbitmqcollector.location.application.port;

import java.util.List;

import org.rabbitmqcollector.location.domain.VehicleStatus;
import org.rabbitmqcollector.location.infrastructure.jpa.entity.VehicleInformationEntity;

public interface VehicleInformationRepository {
	VehicleInformationEntity findVehicleById(Long id);

	String findVehicleIdByVehicleNumber(Long id);

	List<VehicleInformationEntity> findByStatus(VehicleStatus status);
}