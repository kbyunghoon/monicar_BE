package org.eventhub.infrastructure.repository.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import org.eventhub.domain.Alarm;
import org.eventhub.domain.AlarmStatus;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "alarm")
public class AlarmEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "alarm_id")
	private Long id;

	private Long managerId;

	private Long vehicleId;

	private Integer drivingDistance;

	@Enumerated(value = EnumType.STRING)
	private AlarmStatus status;

	private boolean isChecked;

	@CreatedDate
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime updatedAt;

	private LocalDateTime deletedAt;

	public static AlarmEntity from(Alarm alarm) {
		AlarmEntity alarmEntity = new AlarmEntity();
		alarmEntity.id = alarm.getId();
		alarmEntity.managerId = alarm.getManagerId();
		alarmEntity.vehicleId = alarm.getVehicleId();
		alarmEntity.drivingDistance = alarm.getDrivingDistance();
		alarmEntity.status = alarm.getStatus();
		alarmEntity.isChecked = alarm.getIsChecked();
		alarmEntity.createdAt = alarm.getCreatedAt();
		alarmEntity.updatedAt = alarm.getUpdatedAt();
		alarmEntity.deletedAt = alarm.getDeletedAt();
		return alarmEntity;
	}

	public Alarm toDomain() {
		return Alarm.builder()
			.id(id)
			.managerId(managerId)
			.vehicleId(vehicleId)
			.drivingDistance(drivingDistance)
			.status(status)
			.isChecked(isChecked)
			.createdAt(createdAt)
			.updatedAt(updatedAt)
			.deletedAt(deletedAt)
			.build();
	}
}
