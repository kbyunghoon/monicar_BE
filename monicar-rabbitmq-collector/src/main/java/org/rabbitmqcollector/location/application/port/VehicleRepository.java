package org.rabbitmqcollector.location.application.port;

import org.rabbitmqcollector.location.infrastructure.jpa.entity.VehicleInformationEntity;

public interface VehicleRepository {
	VehicleInformationEntity findVehicleById(Long id);

	String findVehicleIdByVehicleNumber(Long id);
}