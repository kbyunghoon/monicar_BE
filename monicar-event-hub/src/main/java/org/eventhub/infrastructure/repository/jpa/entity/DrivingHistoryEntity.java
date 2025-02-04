package org.eventhub.infrastructure.repository.jpa.entity;

import java.time.LocalDateTime;

import org.eventhub.domain.DrivingHistory;
import org.eventhub.domain.UsePurpose;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "driving_history")
@SQLRestriction("deleted_at IS NULL")
public class DrivingHistoryEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "driving_history_id")
	private Long id;

	private Long vehicleId;

	private Long departmentId;

	private String driverEmail;

	private Long initialOdometer;

	private Long finalOdometer;

	private Integer drivingDistance;

	@Enumerated(value = EnumType.STRING)
	private UsePurpose usePurpose;

	private LocalDateTime startTime;

	private LocalDateTime endTime;

	@CreatedDate
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime updatedAt;

	private LocalDateTime deletedAt;

	public static DrivingHistoryEntity from(DrivingHistory drivingHistory) {
		DrivingHistoryEntity drivingHistoryEntity = new DrivingHistoryEntity();
		drivingHistoryEntity.id = drivingHistory.getId();
		drivingHistoryEntity.vehicleId = drivingHistory.getVehicleId();
		drivingHistoryEntity.departmentId = drivingHistory.getDepartmentId();
		drivingHistoryEntity.driverEmail = drivingHistory.getDriverEmail();
		drivingHistoryEntity.initialOdometer = drivingHistory.getInitialOdometer();
		drivingHistoryEntity.finalOdometer = drivingHistory.getFinalOdometer();
		drivingHistoryEntity.drivingDistance = drivingHistory.getDrivingDistance();
		drivingHistoryEntity.usePurpose = drivingHistory.getUsePurpose();
		drivingHistoryEntity.startTime = drivingHistory.getStartTime();
		drivingHistoryEntity.endTime = drivingHistory.getEndTime();
		drivingHistoryEntity.createdAt = drivingHistory.getCreatedAt();
		drivingHistoryEntity.updatedAt = drivingHistory.getUpdatedAt();
		drivingHistoryEntity.deletedAt = drivingHistory.getDeletedAt();
		return drivingHistoryEntity;
	}

	public DrivingHistory toDomain() {
		return DrivingHistory.builder()
			.id(id)
			.vehicleId(vehicleId)
			.departmentId(departmentId)
			.driverEmail(driverEmail)
			.initialOdometer(initialOdometer)
			.finalOdometer(finalOdometer)
			.drivingDistance(drivingDistance)
			.usePurpose(usePurpose)
			.startTime(startTime)
			.endTime(endTime)
			.createdAt(createdAt)
			.updatedAt(updatedAt)
			.deletedAt(deletedAt)
			.build();
	}
}
