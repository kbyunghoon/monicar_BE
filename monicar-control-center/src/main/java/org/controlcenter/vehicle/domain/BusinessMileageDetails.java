package org.controlcenter.vehicle.domain;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BusinessMileageDetails {
	private double commutingMileage;
	private double businessMileage;

	@QueryProjection
	public BusinessMileageDetails(double commutingMileage, double businessMileage) {
		this.commutingMileage = commutingMileage;
		this.businessMileage = businessMileage;
	}
}