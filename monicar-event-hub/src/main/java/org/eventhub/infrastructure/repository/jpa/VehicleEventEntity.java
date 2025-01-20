package org.eventhub.infrastructure.repository.jpa;

import java.time.LocalDateTime;

import org.eventhub.domain.VehicleEvent;
import org.eventhub.domain.VehicleEventType;
import org.springframework.data.annotation.CreatedDate;
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
@Entity(name = "vehicle_event")
public class VehicleEventEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vehicle_event_id")
	private Long id;

	private Long vehicleId;

	@Enumerated(value = EnumType.STRING)
	private VehicleEventType type;

	private LocalDateTime eventAt;

	@CreatedDate
	private LocalDateTime createdAt;

	public static VehicleEventEntity from(VehicleEvent vehicleEvent) {
		VehicleEventEntity vehicleEventEntity = new VehicleEventEntity();
		vehicleEventEntity.id = vehicleEvent.getId();
		vehicleEventEntity.vehicleId = vehicleEvent.getVehicleId();
		vehicleEventEntity.type = vehicleEvent.getType();
		vehicleEventEntity.eventAt = vehicleEvent.getEventAt();
		vehicleEventEntity.createdAt = vehicleEvent.getCreatedAt();
		return vehicleEventEntity;
	}

	public VehicleEvent toDomain() {
		return VehicleEvent.builder()
			.id(id)
			.vehicleId(vehicleId)
			.type(type)
			.eventAt(eventAt)
			.createdAt(createdAt)
			.build();
	}
}
