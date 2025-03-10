package org.controlcenter.vehicle.presentation.dto;

import java.time.LocalDateTime;

public record RouteResponse(
	Integer lat,
	Integer lng,
	Integer spd,
	LocalDateTime timestamp
) {
}
