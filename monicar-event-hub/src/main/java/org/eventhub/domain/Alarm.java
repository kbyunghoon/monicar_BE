package org.eventhub.domain;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Alarm {
	private Long id;
	private String managerId;
	private Long vehicleId;
	private Integer drivingDistance;
	private AlarmStatus status;
	private Boolean isChecked;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private LocalDateTime deletedAt;
}
