package org.controlcenter.vehicle.application.port;

import java.util.List;

import org.controlcenter.vehicle.domain.VehicleType;

public interface VehicleTypeRepository {
	List<VehicleType> findAll();
}
