package org.controlcenter.alarm.domain;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Alarm {
	private Long id;
	private String managerId;
	private long vehicleId;
	private Integer drivingDistance;
	private AlarmStatus status;
	private boolean isChecked;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private LocalDateTime deletedAt;

	public static Alarm create(AlarmCreate alarmCreate) {
		return Alarm.builder()
			.managerId(alarmCreate.getManagerId())
			.vehicleId(alarmCreate.getVehicleId())
			.drivingDistance(alarmCreate.getDrivingDistance())
			.status(alarmCreate.getStatus())
			.build();
	}
}
