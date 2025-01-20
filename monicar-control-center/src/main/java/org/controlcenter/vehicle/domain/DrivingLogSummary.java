package org.controlcenter.vehicle.domain;

import lombok.Getter;

import org.controlcenter.history.domain.UsePurpose;

@Getter
public class DrivingLogSummary {
	int totalCount = 0;
	int commuteCount = 0;

	public void accumulate(DrivingLogDetailsContent log) {
		totalCount++;
		if (log.getDrivingInfo() != null
			&& log.getDrivingInfo().getBusinessDrivingDetails() != null
			&& UsePurpose.COMMUTE.equals(
			log.getDrivingInfo().getBusinessDrivingDetails().getUsePurpose())) {
			commuteCount++;
		}
	}

	public void combine(DrivingLogSummary other) {
		this.totalCount += other.totalCount;
		this.commuteCount += other.commuteCount;
	}

	public int getCommutePercent() {
		return totalCount == 0 ? 0 : (int)((commuteCount * 100) / totalCount);
	}
}
