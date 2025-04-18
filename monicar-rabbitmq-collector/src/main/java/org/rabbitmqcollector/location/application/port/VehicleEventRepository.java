package org.rabbitmqcollector.location.application.port;

import org.rabbitmqcollector.location.domain.VehicleEvent;

public interface VehicleEventRepository {
	VehicleEvent save(VehicleEvent vehicleEvent);
}
