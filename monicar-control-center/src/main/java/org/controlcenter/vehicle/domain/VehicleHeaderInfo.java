package org.controlcenter.vehicle.domain;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class VehicleHeaderInfo {
	private Long vehicleId;
	private String vehicleNumber;
	private String vehicleTypesName;
	private Long companyId;
	private String companyName;
	private String businessRegistrationNumber;
	private VehicleStatus status;

	@QueryProjection
	public VehicleHeaderInfo(Long vehicleId, String vehicleNumber, String vehicleTypesName, Long companyId,
		String companyName, String businessRegistrationNumber, VehicleStatus status) {
		this.vehicleId = vehicleId;
		this.vehicleNumber = vehicleNumber;
		this.vehicleTypesName = vehicleTypesName;
		this.companyId = companyId;
		this.companyName = companyName;
		this.businessRegistrationNumber = businessRegistrationNumber;
		this.status = status;
	}
}
