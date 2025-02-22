package org.controlcenter.vehicle.presentation.dto;

import lombok.Builder;

@Builder
public record VehicleEngineStatusResponse(
	Integer allVehicles,
	Integer engineOnVehicles,
	Integer engineOffVehicles
) {
}
