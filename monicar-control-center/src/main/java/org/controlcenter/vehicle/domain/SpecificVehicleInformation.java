package org.controlcenter.vehicle.domain;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SpecificVehicleInformation {
	private String vehicleNumber;
	private String vehicleModel;

	@QueryProjection
	public SpecificVehicleInformation(String vehicleNumber, String vehicleModel) {
		this.vehicleNumber = vehicleNumber;
		this.vehicleModel = vehicleModel;
	}
}
