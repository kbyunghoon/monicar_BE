package org.controlcenter.vehicle.domain;

import org.controlcenter.history.domain.UsePurpose;

import lombok.Getter;

@Getter
public class DrivingLogSummary {
	int totalCount = 0;
	int normalCount = 0;
	int businessDrivingDistance = 0;

	public void accumulate(DrivingLogDetailsContent log) {
		totalCount++;
		if (log.getDrivingInfo() != null
			&& log.getDrivingInfo().getBusinessDrivingDetails() != null
			&& UsePurpose.NORMAL.equals(
			log.getDrivingInfo().getBusinessDrivingDetails().getUsePurpose())) {
			normalCount++;
			businessDrivingDistance += log.getDrivingInfo().getTotalDriving();
		}
	}

	public void combine(DrivingLogSummary other) {
		this.totalCount += other.totalCount;
		this.normalCount += other.normalCount;
	}

	public int getCommutePercent() {
		return totalCount == 0 ? 0 : (int)((normalCount * 100) / totalCount);
	}
}
