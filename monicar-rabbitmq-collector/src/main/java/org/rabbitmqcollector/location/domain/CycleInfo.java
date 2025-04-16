package org.rabbitmqcollector.location.domain;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CycleInfo {
	private long id;
	private long vehicleId;
	private GpsStatus status;
	private int lat;
	private int lng;
	private Integer ang;
	private Integer spd;
	private LocalDateTime intervalAt;
	private LocalDateTime createdAt;
}
