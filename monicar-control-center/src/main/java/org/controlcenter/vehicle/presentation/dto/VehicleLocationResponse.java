package org.controlcenter.vehicle.presentation.dto;

import lombok.Builder;

@Builder
public record VehicleLocationResponse(
	Long vehicleId,
	String vehicleNumber,
	VehicleModalResponse.RecentCycleInfo recentCycleInfo,
	String status
) {
}
