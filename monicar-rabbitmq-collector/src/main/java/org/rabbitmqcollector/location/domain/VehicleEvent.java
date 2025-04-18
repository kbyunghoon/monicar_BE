package org.rabbitmqcollector.location.domain;

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

	public boolean isTypeOn() {
		return type.isOn();
	}

	public boolean isTypeOff() {
		return type.isOff();
	}
}
