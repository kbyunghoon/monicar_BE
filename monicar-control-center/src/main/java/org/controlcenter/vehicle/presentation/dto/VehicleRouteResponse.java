package org.controlcenter.vehicle.presentation.dto;

import java.util.List;

import lombok.Builder;

@Builder
public record VehicleRouteResponse(
	String vehicleNumber,
	List<RouteResponse> routes
) {
}
