package org.eventhub.domain;

import java.time.LocalDateTime;

public class VehicleOffEventCreate extends VehicleEventCreateAbs {

	public VehicleOffEventCreate(Long vehicleId, VehicleEventType eventType, LocalDateTime eventAt) {
		super(vehicleId, eventType, eventAt);
	}

	public static VehicleOffEventCreate of(Long vehicleId, VehicleEventType eventType, LocalDateTime eventAt) {
		return new VehicleOffEventCreate(vehicleId, eventType, eventAt);
	}
}
