package org.controlcenter.vehicle.presentation.dto;

import org.controlcenter.vehicle.domain.DrivingLog;

import lombok.Builder;

@Builder
public record DrivingLogResponse(
	Long id,
	String vehicleNumber,
	String vehicleModel,
	String status
) {
	public static DrivingLogResponse from(DrivingLog drivingLog) {
		return DrivingLogResponse.builder()
			.id(drivingLog.getId())
			.vehicleNumber(drivingLog.getVehicleNumber())
			.vehicleModel(drivingLog.getVehicleModel())
			.status(drivingLog.getStatus())
			.build();
	}
}
