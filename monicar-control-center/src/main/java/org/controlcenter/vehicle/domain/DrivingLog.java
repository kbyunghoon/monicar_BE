package org.controlcenter.vehicle.domain;

import com.querydsl.core.annotations.QueryProjection;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DrivingLog {
	private Long id;
	private String vehicleNumber;
	private String vehicleModel;
	private Integer drivingDays;
	private Integer totalDistance;
	private VehicleStatus status;

	@QueryProjection
	public DrivingLog(Long id, String vehicleNumber, String vehicleModel, Integer drivingDays, Integer totalDistance,
		VehicleStatus status) {
		this.id = id;
		this.vehicleNumber = vehicleNumber;
		this.vehicleModel = vehicleModel;
		this.drivingDays = drivingDays;
		this.totalDistance = totalDistance;
		this.status = status;
	}
}
