package org.controlcenter.vehicle.domain;

import com.querydsl.core.annotations.QueryProjection;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DrivingInfo {
	private double mileageBefore;
	private double mileageAfter;
	private double totalMileage;
	private BusinessMileageDetails businessMileageDetails;
	private final String notes = "일반업무용";

	@QueryProjection
	public DrivingInfo(double mileageBefore, double mileageAfter, double totalMileage,
		BusinessMileageDetails businessMileageDetails) {
		this.mileageBefore = mileageBefore;
		this.mileageAfter = mileageAfter;
		this.totalMileage = totalMileage;
		this.businessMileageDetails = BusinessMileageDetails.builder()
			.commutingMileage(businessMileageDetails.getCommutingMileage())
			.businessMileage(businessMileageDetails.getBusinessMileage())
			.build();
	}
}
