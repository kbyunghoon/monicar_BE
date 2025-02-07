package org.controlcenter.vehicle.presentation.dto;

import java.time.LocalDateTime;

public record RouteResponseWithAng(
	Integer lat,
	Integer lng,
	Integer ang,
	LocalDateTime timestamp
) {
}
