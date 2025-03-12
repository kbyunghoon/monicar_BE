package org.controlcenter.vehicle.presentation.dto;

import lombok.Builder;

@Builder
public record VehicleEngineStatusResponse(
	Long allVehicles,
	Long engineOnVehicles,
	Long engineOffVehicles
) {
}
