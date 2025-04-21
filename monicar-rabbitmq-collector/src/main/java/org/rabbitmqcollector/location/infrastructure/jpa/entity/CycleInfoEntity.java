package org.rabbitmqcollector.location.infrastructure.jpa.entity;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import org.rabbitmqcollector.location.domain.CycleInfo;
import org.rabbitmqcollector.location.domain.GpsStatus;
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

