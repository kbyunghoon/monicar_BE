package org.rabbitmqcollector.location.infrastructure.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import org.rabbitmqcollector.location.domain.CycleInfo;
import org.rabbitmqcollector.location.domain.GpsStatus;
import org.rabbitmqcollector.location.presentation.dto.CarLocationMessage;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "cycle_info")
public class CycleInfoEntity implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cycle_info_id")
	private Long id;

	private Long vehicleId;

	@Enumerated(EnumType.STRING)
	private GpsStatus status;

	private Integer lat;

	private Integer lng;

	private Integer ang;

	private Integer spd;

	private LocalDateTime intervalAt;

	@CreatedDate
	private LocalDateTime createdAt;

	public static CycleInfoEntity from(CycleInfo cycleInfo) {
		CycleInfoEntity cycleInfoEntity = new CycleInfoEntity();
		cycleInfoEntity.id = cycleInfo.getId();
		cycleInfoEntity.vehicleId = cycleInfo.getVehicleId();
		cycleInfoEntity.status = cycleInfo.getStatus();
		cycleInfoEntity.lat = cycleInfo.getLat();
		cycleInfoEntity.lng = cycleInfo.getLng();
		cycleInfoEntity.ang = cycleInfo.getAng();
		cycleInfoEntity.spd = cycleInfo.getSpd();
		cycleInfoEntity.intervalAt = cycleInfo.getIntervalAt();
		cycleInfoEntity.createdAt = cycleInfo.getCreatedAt();
		return cycleInfoEntity;
	}

	public static CycleInfoEntity from(CarLocationMessage carLocationMessage) {
		CycleInfoEntity cycleInfoEntity = new CycleInfoEntity();
		cycleInfoEntity.vehicleId = carLocationMessage.id();
		cycleInfoEntity.status = GpsStatus.A;
		cycleInfoEntity.lat = carLocationMessage.lat();
		cycleInfoEntity.lng = carLocationMessage.lng();
		cycleInfoEntity.ang = 0;
		cycleInfoEntity.spd = 80;
		cycleInfoEntity.intervalAt = carLocationMessage.timestamp();
		cycleInfoEntity.createdAt = carLocationMessage.timestamp();
		return cycleInfoEntity;
	}

	public CycleInfo toDomain() {
		return CycleInfo.builder()
			.id(id)
			.vehicleId(vehicleId)
			.status(status)
			.lat(lat)
			.lng(lng)
			.ang(ang)
			.spd(spd)
			.intervalAt(intervalAt)
			.createdAt(createdAt)
			.build();
	}
}

