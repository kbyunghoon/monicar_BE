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
	@Builder.Default
	private LocalDateTime offTime = null;
	private String gpsStatus;
	@Builder.Default
	private double latitude = 0;
	@Builder.Default
	private double longitude = 0;
	@Builder.Default
	private int direction = 0;
	@Builder.Default
	private int speed = 0;
	private int totalDistance;
}
