package org.controlcenter.vehicle.presentation.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RouteResponse(
	BigDecimal lat,
	BigDecimal lng,
	Integer spd,
	LocalDateTime timestamp
) {
}
