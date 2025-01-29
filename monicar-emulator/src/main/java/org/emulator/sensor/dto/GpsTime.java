package org.emulator.sensor.dto;

import java.time.LocalDateTime;

public record GpsTime(
	Gps location,
	LocalDateTime intervalAt
) {
	public static GpsTime create(Gps location) {
		return new GpsTime(location, LocalDateTime.now());
	}
}
