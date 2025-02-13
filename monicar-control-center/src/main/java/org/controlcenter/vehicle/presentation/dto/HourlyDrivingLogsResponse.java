package org.controlcenter.vehicle.presentation.dto;

import java.util.List;

import org.controlcenter.vehicle.domain.HourlyDrivingLogs;

public record HourlyDrivingLogsResponse(
	String vehicleNumber,
	List<HourlyDrivingLogs> content
) {
	public static HourlyDrivingLogsResponse from(String vehicleNumber, List<HourlyDrivingLogs> content) {
		return new HourlyDrivingLogsResponse(
			vehicleNumber, content
		);
	}
}
