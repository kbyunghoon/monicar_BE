package org.controlcenter.alarm.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AlarmCreate {
	private String managerId;
	private long vehicleId;
	private Integer drivingDistance;
	private AlarmStatus status;

	private AlarmCreate(
		String managerId,
		long vehicleId,
		Integer drivingDistance,
		AlarmStatus status
	) {
		this.managerId = managerId;
		this.vehicleId = vehicleId;
		this.drivingDistance = drivingDistance;
		this.status = status;
	}
}
