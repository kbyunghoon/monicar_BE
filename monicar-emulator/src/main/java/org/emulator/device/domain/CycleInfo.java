package org.emulator.device.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.emulator.device.VehicleConstant;

@AllArgsConstructor
@Getter
public class CycleInfo {
	private LocalDateTime oTime;
	private GpsStatus gpsStatus;
	private long latitude;
	private long longitude;
	private int direction;
	private int speed;
	private int totalDistance;
	private int battery;

	public static CycleInfo create(
		LocalDateTime oTime,
		GpsStatus gpsStatus,
		double latitude,
		double longitude,
		int direction,
		int speed,
		int totalDistance,
		int battery
	) {
		long lat = (long)(latitude * VehicleConstant.MIL);
		long lon = (long)(longitude * VehicleConstant.MIL);
		return new CycleInfo(
			oTime,
			gpsStatus,
			lat,
			lon,
			direction,
			speed,
			totalDistance,
			battery
		);
	}
}
