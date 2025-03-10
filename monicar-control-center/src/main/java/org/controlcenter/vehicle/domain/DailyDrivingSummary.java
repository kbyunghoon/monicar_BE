package org.controlcenter.vehicle.domain;

import java.sql.Date;
import java.time.LocalDate;

import com.querydsl.core.annotations.QueryProjection;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DailyDrivingSummary {
	private LocalDate drivingDate;
	private Integer totalDistance;
	private Long totalDrivingSeconds;

	@QueryProjection
	public DailyDrivingSummary(Date drivingDate, Integer totalDistance, Long totalDrivingSeconds) {
		this.drivingDate = drivingDate.toLocalDate();
		this.totalDistance = totalDistance;
		this.totalDrivingSeconds = totalDrivingSeconds;
	}
}
