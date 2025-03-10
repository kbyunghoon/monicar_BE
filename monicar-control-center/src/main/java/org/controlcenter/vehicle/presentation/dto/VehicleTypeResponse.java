package org.controlcenter.vehicle.presentation.dto;

import org.controlcenter.vehicle.domain.VehicleType;

import lombok.Builder;

@Builder

public record VehicleTypeResponse(
	Long id,
	String vehicleName
) {
	public static VehicleTypeResponse from(VehicleType vehicleType) {
		return VehicleTypeResponse.builder()
			.id(vehicleType.getId())
			.vehicleName(vehicleType.getVehicleTypesName())
			.build();
	}
}
