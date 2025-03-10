package org.controlcenter.vehicle.presentation.dto;

import org.controlcenter.vehicle.domain.VehicleInformation;

import lombok.Builder;

@Builder
public record SimpleRankResponse(
	Long id,
	String vehicleNumber,
	int distance
) {
	public static SimpleRankResponse from(VehicleInformation vehicleInformation) {
		return SimpleRankResponse.builder()
			.id(vehicleInformation.getId())
			.vehicleNumber(vehicleInformation.getVehicleNumber())
			.distance(vehicleInformation.getSum())
			.build();
	}
}
