package org.controlcenter.vehicle.domain;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BusinessInfo {
	private String businessName;
	private String businessRegistrationNumber;
}
