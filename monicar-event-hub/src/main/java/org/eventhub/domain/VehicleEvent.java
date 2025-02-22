package org.eventhub.domain;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class VehicleEvent {
	private Long id;
	private Long vehicleId;
	private VehicleEventType type;
	private LocalDateTime eventAt;
	private LocalDateTime createdAt;

	public static VehicleEvent create(VehicleEventCreateAbs vehicleOnEventCreate) {
		return VehicleEvent.builder()
			.vehicleId(vehicleOnEventCreate.getVehicleId())
			.type(vehicleOnEventCreate.getEventType())
			.eventAt(vehicleOnEventCreate.getEventAt())
			.build();
	}

	public boolean isTypeOn() {
		return type.isOn();
	}

	public boolean isTypeOff() {
		return type.isOff();
	}
}
