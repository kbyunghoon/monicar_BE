package org.emulator.device.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import org.emulator.device.VehicleConstant;

@AllArgsConstructor
@Getter
public class OnInfo {
	private LocalDateTime onTime;
	private LocalDateTime offTime;
	private GpsStatus gpsStatus;
	private long latitude;
	private long longitude;
	private int direction;
	private int speed;
	private int totalDistance;

	public static OnInfo create(
		LocalDateTime onTime,
		GpsStatus gpsStatus,
		double latitude,
		double longitude,
		int totalDistance
	) {
		long lat = (long)(latitude * VehicleConstant.MIL);
		long lon = (long)(longitude * VehicleConstant.MIL);

		return new OnInfo(
			onTime,
			null,
			gpsStatus,
			lat,
			lon,
			0,
			0,
			totalDistance);
	}
}
