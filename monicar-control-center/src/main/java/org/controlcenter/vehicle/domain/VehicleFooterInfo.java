package org.controlcenter.vehicle.domain;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class VehicleFooterInfo {
	private int taxPeriodDistance;
	private int taxPeriodBusinessDistance;
	private int businessUseRatio;

	@QueryProjection
	public VehicleFooterInfo(int taxPeriodDistance, int taxPeriodBusinessDistance, int businessUseRatio) {
		this.taxPeriodDistance = taxPeriodDistance;
		this.taxPeriodBusinessDistance = taxPeriodBusinessDistance;
		this.businessUseRatio = businessUseRatio;
	}
}
