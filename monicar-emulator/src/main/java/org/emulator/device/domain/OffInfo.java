package org.emulator.device.domain;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

import org.emulator.device.infrastructure.external.command.vo.Direction;
import org.emulator.device.infrastructure.external.command.vo.Geo;
import org.emulator.device.infrastructure.external.command.vo.Speed;
import org.emulator.device.infrastructure.external.command.vo.TotalDistance;

@Getter
@Builder
public class OffInfo {
	private LocalDateTime onTime;
	private LocalDateTime offTime;
	private GpsStatus gpsStatus;
	private Geo geo;
	private Direction direction;
	private Speed speed;
	private TotalDistance fromOnToOffDistance;

	public static OffInfo create(
		LocalDateTime offTime,
		GpsStatus gpsStatus,
		double latitude,
		double longitude,
		int fromOnToOffDistance
	) {
		return OffInfo.builder()
			.onTime(null)
			.offTime(offTime)
			.gpsStatus(gpsStatus)
			.geo(new Geo(latitude, longitude))
			.direction(new Direction())
			.speed(new Speed())
			.fromOnToOffDistance(new TotalDistance(fromOnToOffDistance))
			.build();
	}
}
