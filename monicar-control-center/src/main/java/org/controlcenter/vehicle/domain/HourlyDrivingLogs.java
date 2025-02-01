package org.controlcenter.vehicle.domain;

import com.querydsl.core.annotations.QueryProjection;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HourlyDrivingLogs {
	private long startTime;
	private long endTime;
	private int drivingDistance;
	private double lat;
	private double lng;

	@QueryProjection
	public HourlyDrivingLogs(LocalDateTime startTime, LocalDateTime endTime, int drivingDistance, double lat, double lng) {
		this.startTime = Timestamp.valueOf(startTime).getTime();
		this.endTime = Timestamp.valueOf(endTime).getTime();
		this.drivingDistance = drivingDistance;
		this.lat = lat;
		this.lng = lng;
	}
}