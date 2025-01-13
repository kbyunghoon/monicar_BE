package org.controlcenter.vehicle.domain;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BusinessInfo {
	private long businessId;
	private String businessName;
	private String businessRegistrationNumber;

	@QueryProjection
	public BusinessInfo(long businessId, String businessName, String businessRegistrationNumber) {
		this.businessId = businessId;
		this.businessName = businessName;
		this.businessRegistrationNumber = businessRegistrationNumber;
	}
}
