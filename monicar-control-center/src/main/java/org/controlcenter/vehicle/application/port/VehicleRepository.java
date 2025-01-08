package org.controlcenter.vehicle.application.port;

import java.util.Optional;

import org.controlcenter.vehicle.domain.VehicleInformation;

public interface VehicleRepository {
	Optional<VehicleInformation> findById(Long vehicleId);
}
