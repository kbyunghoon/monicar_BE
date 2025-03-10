package org.eventhub.application.port;

import java.util.Optional;

import org.eventhub.domain.VehicleEvent;

public interface VehicleEventRepository {
	VehicleEvent save(VehicleEvent vehicleEvent);

	Optional<VehicleEvent> findLatestByVehicleId(long vehicleId);
}
