package org.controlcenter.vehicle.domain;

import org.controlcenter.history.domain.UsePurpose;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BusinessDrivingDetails {
	private UsePurpose usePurpose;
	private Integer drivingDistance;

	@QueryProjection
	public BusinessDrivingDetails(UsePurpose usePurpose, Integer drivingDistance) {
		this.usePurpose = usePurpose;
		this.drivingDistance = drivingDistance;
	}
}