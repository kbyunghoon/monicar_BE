package org.controlcenter.vehicle.infrastructure.jpa.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.controlcenter.vehicle.domain.VehicleInformation;
import org.controlcenter.vehicle.domain.VehicleStatus;
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

	public static VehicleInformationEntity from(VehicleInformation vehicleInformation) {
		VehicleInformationEntity vehicleInformationEntity = new VehicleInformationEntity();
		vehicleInformationEntity.id = vehicleInformation.getId();
		vehicleInformationEntity.companyId = vehicleInformation.getCompanyId();
		vehicleInformationEntity.vehicleTypeId = vehicleInformation.getVehicleTypeId();
		vehicleInformationEntity.vehicleNumber = vehicleInformation.getVehicleNumber();
		vehicleInformationEntity.mdn = vehicleInformation.getMdn();
		vehicleInformationEntity.tid = vehicleInformation.getTid();
		vehicleInformationEntity.mid = vehicleInformation.getMid();
		vehicleInformationEntity.pv = vehicleInformation.getPv();
		vehicleInformationEntity.did = vehicleInformation.getDid();
		vehicleInformationEntity.drivingDays = vehicleInformation.getDrivingDays();
		vehicleInformationEntity.sum = vehicleInformation.getSum();
		vehicleInformationEntity.lat = vehicleInformation.getLat();
		vehicleInformationEntity.lng = vehicleInformation.getLat();
		vehicleInformationEntity.status = vehicleInformation.getStatus();
		vehicleInformationEntity.deliveryDate = vehicleInformation.getDeliveryDate();
		vehicleInformationEntity.createdAt = vehicleInformation.getCreatedAt();
		vehicleInformationEntity.updatedAt = vehicleInformation.getUpdatedAt();
		vehicleInformationEntity.deletedAt = vehicleInformation.getDeletedAt();
		return vehicleInformationEntity;
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
			.lat(lat)
			.lng(lng)
			.status(status)
			.deliveryDate(deliveryDate)
			.createdAt(createdAt)
			.updatedAt(updatedAt)
			.deletedAt(deletedAt)
			.build();
	}
}
