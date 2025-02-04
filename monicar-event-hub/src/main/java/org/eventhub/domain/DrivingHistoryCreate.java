package org.eventhub.domain;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record DrivingHistoryCreate(
	Long vehicleId,
	Long companyId,
	Long totalDistance,
	Integer fromOnToOffDistance,
	LocalDateTime recentEventAt,
	LocalDateTime offTime
) {
	public static DrivingHistoryCreate of(
		Long vehicleId,
		Long companyId,
		Long totalDistance,
		Integer fromOnToOffDistance,
		LocalDateTime recentEventAt,
		LocalDateTime offTime
	) {
		return DrivingHistoryCreate.builder()
			.vehicleId(vehicleId)
			.companyId(companyId)
			.totalDistance(totalDistance)
			.fromOnToOffDistance(fromOnToOffDistance)
			.recentEventAt(recentEventAt)
			.offTime(offTime)
			.build();
	}
}
