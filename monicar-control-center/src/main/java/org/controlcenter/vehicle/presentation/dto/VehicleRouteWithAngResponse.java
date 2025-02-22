package org.controlcenter.vehicle.presentation.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record VehicleRouteWithAngResponse(
	String vehicleNumber,
	List<RouteResponseWithAng> routes
) {
}
