package org.emulator.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class OnInfo {
	private LocalDateTime onTime;
	private LocalDateTime offTime;
	private GpsStatus gpsStatus;
	private long latitude;
	private long longitude;
	private int direction;
	private int speed;
	private int totalDistance;

	public static OnInfo createOnInfo(
		LocalDateTime onTime,
		GpsStatus gpsStatus,
		long latitude,
		long longitude,
		int totalDistance
	) {
		return new OnInfo(
			onTime, null, gpsStatus, latitude, longitude, 0, 0, totalDistance);
	}
}
