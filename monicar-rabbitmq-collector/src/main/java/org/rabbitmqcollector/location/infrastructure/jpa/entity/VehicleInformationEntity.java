package org.rabbitmqcollector.location.infrastructure.jpa.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.SQLRestriction;
import org.rabbitmqcollector.location.domain.VehicleInformation;
import org.rabbitmqcollector.location.domain.VehicleStatus;
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
@Entity(name = "vehicle_information")
@SQLRestriction("deleted_at IS NULL")
public class VehicleInformationEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vehicle_id")
	private Long id;

	private Long companyId;

	private Long vehicleTypeId;

	private String vehicleNumber;

	private Long mdn;

	private String tid;

	private Integer mid;

	private Integer pv;

	private Integer did;

	private Integer drivingDays;

	private Integer sum;

	private Integer lat;

	private Integer lng;

	@Enumerated(value = EnumType.STRING)
	private VehicleStatus status;

	private LocalDate deliveryDate;

	@CreatedDate
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime updatedAt;

	private LocalDateTime deletedAt;

	public VehicleStatus getStatus() {
		return status;
	}

	public void updateStatusAndLocationIfNeeded(int lat, int lng) {
		if (this.status == VehicleStatus.NOT_DRIVEN) {
			this.status = VehicleStatus.IN_OPERATION;
		}
		this.lat = lat;
		this.lng = lng;
	}

	public void updateStatus(VehicleStatus status) {
		this.status = status;
	}

	public VehicleInformation toDomain() {
		return VehicleInformation.builder()
			.id(id)
			.companyId(companyId)
			.vehicleTypeId(vehicleTypeId)
			.vehicleNumber(vehicleNumber)
			.mdn(mdn)
			.tid(tid)
			.mid(mid)
			.pv(pv)
			.did(did)
			.drivingDays(drivingDays)
			.sum(sum)
			.deliveryDate(deliveryDate)
			.createdAt(createdAt)
			.updatedAt(updatedAt)
			.deletedAt(deletedAt)
			.build();
	}
}
