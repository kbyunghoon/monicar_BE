package org.eventhub.domain;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public abstract class VehicleEventCreateAbs {
	protected final Long vehicleId;
	protected final VehicleEventType eventType;
	protected final LocalDateTime eventAt;

	protected VehicleEventCreateAbs(Long vehicleId, VehicleEventType eventType, LocalDateTime eventAt) {
		this.vehicleId = vehicleId;
		this.eventType = eventType;
		this.eventAt = eventAt;
	}
}
