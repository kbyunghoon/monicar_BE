package org.emulator.device.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class OnInfo {
	private static final long MIL = 1000_000;

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
		double latitude,
		double longitude,
		int totalDistance
	) {
		long lat = (long) (latitude * MIL);
		long lon =(long) (longitude * MIL);
		return new OnInfo(
			onTime, null, gpsStatus, lat, lon, 0, 0, totalDistance);
	}
}
