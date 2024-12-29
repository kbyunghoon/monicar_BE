package org.emulator.domain;

import java.time.LocalDateTime;

public class OffInfo {
	private LocalDateTime vehicleOnTime;
	private LocalDateTime vehicleOffTime;
	private String gpsStatus;
	private double latitude;
	private double longitude;
	private int direction;
	private int speed;
	private int totalDistance;

	public OffInfo(LocalDateTime vehicleOnTime, LocalDateTime vehicleOffTime, String gpsStatus, double latitude,
		double longitude, int totalDistance) {
		this.vehicleOnTime = vehicleOnTime;
		this.vehicleOffTime = vehicleOffTime;
		this.gpsStatus = gpsStatus;
		this.latitude = latitude;
		this.longitude = longitude;
		this.direction = 0;
		this.speed = 0;
		this.totalDistance = totalDistance;
	}
}
