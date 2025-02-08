package org.controlcenter.vehicle.presentation.dto;

import org.controlcenter.vehicle.domain.DrivingLog;

import lombok.Builder;

import org.controlcenter.vehicle.domain.VehicleStatus;

@Builder
public record DrivingLogResponse(
	Long id,
	String vehicleNumber,
	String vehicleModel,
	Integer drivingDays,
	Integer totalDistance,
	VehicleStatus status
) {
	public static DrivingLogResponse from(DrivingLog drivingLog) {
		return DrivingLogResponse.builder()
			.id(drivingLog.getId())
			.vehicleNumber(drivingLog.getVehicleNumber())
			.vehicleModel(drivingLog.getVehicleModel())
			.drivingDays(drivingLog.getDrivingDays())
			.totalDistance(drivingLog.getTotalDistance())
			.status(drivingLog.getStatus())
			.build();
	}
}
