package org.controlcenter.vehicle.application.port;

import org.controlcenter.vehicle.domain.VehicleEvent;

public interface VehicleEventRepository {
	VehicleEvent save(VehicleEvent vehicleEvent);
}
