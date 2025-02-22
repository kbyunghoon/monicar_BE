package org.controlcenter.vehicle.presentation.dto;

import lombok.Builder;

import org.controlcenter.vehicle.presentation.RouteResponseWithStatus;

import java.util.List;

@Builder
public record VehicleRouteWithStatusResponse(
	String vehicleNumber,
	List<RouteResponseWithStatus> routes
) {
}
