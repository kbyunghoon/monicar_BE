package org.controlcenter.vehicle.domain;

import com.querydsl.core.annotations.QueryProjection;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DrivingInfo {
	private long drivingBefore;
	private long drivingAfter;
	private Integer totalDriving;
	private BusinessDrivingDetails businessDrivingDetails;
	private final String notes = "일반업무용";

	@QueryProjection
	public DrivingInfo(long drivingBefore, long drivingAfter, Integer totalDriving,
		BusinessDrivingDetails businessDrivingDetails) {
		this.drivingBefore = drivingBefore;
		this.drivingAfter = drivingAfter;
		this.totalDriving = totalDriving;
		this.businessDrivingDetails = BusinessDrivingDetails.builder()
			.drivingDistance(businessDrivingDetails.getDrivingDistance())
			.usePurpose(businessDrivingDetails.getUsePurpose())
			.build();
	}
}
