package org.controlcenter.vehicle.presentation.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record VehicleModalResponse(
	RecentVehicleInfo recentVehicleInfo,
	RecentCycleInfo recentCycleInfo,
	TodayDrivingHistory todayDrivingHistory
) {

	public record RecentVehicleInfo(
		Long vehicleId,
		String vehicleNumber,
		String status,
		LocalDateTime lastEngineOn,
		LocalDateTime lastEngineOff
	) {
	}

	public record RecentCycleInfo(
		Integer speed,
		Integer lat,
		Integer lng,
		LocalDateTime lastUpdated
	) {
	}

	public record TodayDrivingHistory(
		Double distance,
		Integer drivingTime
	) {
	}

}
