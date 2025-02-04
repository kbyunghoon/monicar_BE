package org.eventhub.domain;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DrivingHistory {
	private Long id;
	private Long vehicleId; // vehicle_information vehicle_id
	private Long departmentId;
	private String driverEmail;
	private Long initialOdometer; // finalOdometer - on ~ off 총 주행거리
	private Long finalOdometer; // vehicle_information sum
	private Integer drivingDistance; // on ~ off 총 주행거리
	private UsePurpose usePurpose;
	private LocalDateTime startTime; // 최근 on event event_at
	private LocalDateTime endTime; // offTime
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private LocalDateTime deletedAt;

	public static DrivingHistory of(DrivingHistoryCreate dto) {
		return DrivingHistory.builder()
			.vehicleId(dto.vehicleId())
			.departmentId(1L)
			.driverEmail("john@example.com")
			.initialOdometer(dto.totalDistance() - dto.fromOnToOffDistance())
			.finalOdometer(dto.totalDistance())
			.drivingDistance(dto.fromOnToOffDistance())
			.usePurpose(UsePurpose.COMMUTE)
			.startTime(dto.recentEventAt())
			.endTime(dto.offTime())
			.build();
	}
}
