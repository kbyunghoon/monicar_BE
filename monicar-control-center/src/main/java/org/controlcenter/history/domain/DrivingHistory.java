package org.controlcenter.history.domain;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DrivingHistory {
	private Long id;
	private Long vehicleId;
	private Long departmentId;
	private String driverEmail;
	private Long initialOdometer;
	private Long finalOdometer;
	private Integer drivingDistance;
	private UsePurpose usePurpose;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private LocalDateTime deletedAt;
}
