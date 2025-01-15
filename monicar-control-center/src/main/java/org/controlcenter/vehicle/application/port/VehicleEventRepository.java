package org.controlcenter.vehicle.application.port;

import java.util.Optional;

import org.controlcenter.vehicle.domain.VehicleEvent;

public interface VehicleEventRepository {
	VehicleEvent save(VehicleEvent vehicleEvent);

	Optional<VehicleEvent> findLatestById(long vehicleId);
}
