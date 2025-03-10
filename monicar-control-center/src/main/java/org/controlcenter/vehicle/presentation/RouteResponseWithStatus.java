package org.controlcenter.vehicle.presentation;

import org.controlcenter.vehicle.domain.VehicleStatus;

import java.time.LocalDateTime;

public record RouteResponseWithStatus(
	Integer lat,
	Integer lng,
	Integer spd,
	VehicleStatus status,
	LocalDateTime timestamp
) {
}


