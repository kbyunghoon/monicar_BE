package org.controlcenter.vehicle.presentation.dto;

import java.util.List;

import org.controlcenter.vehicle.domain.DailyDrivingSummary;

public record DailyDrivingLogsResponse(
	String vehicleNumber,
	List<DailyDrivingSummary> content
) {
	public static DailyDrivingLogsResponse from(String vehicleNumber, List<DailyDrivingSummary> content) {
		return new DailyDrivingLogsResponse(
			vehicleNumber, content
		);
	}
}
