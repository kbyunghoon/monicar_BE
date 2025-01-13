package org.controlcenter.vehicle.domain;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SpecificVehicleInformation {
	private String vehicleNumber;
	private String vehicleTypesName;

	@QueryProjection
	public SpecificVehicleInformation(String vehicleNumber, String vehicleTypesName) {
		this.vehicleNumber = vehicleNumber;
		this.vehicleTypesName = vehicleTypesName;
	}
}
