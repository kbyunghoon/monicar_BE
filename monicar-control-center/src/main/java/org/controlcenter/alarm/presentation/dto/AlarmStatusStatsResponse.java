package org.controlcenter.alarm.presentation.dto;

import org.controlcenter.alarm.domain.AlarmStatus;
import org.controlcenter.alarm.domain.AlarmStatusStats;

import lombok.Builder;

@Builder
public record AlarmStatusStatsResponse(
	AlarmStatus status,
	long count
) {
	public static AlarmStatusStatsResponse from(AlarmStatusStats alarmStatusStats) {
		return AlarmStatusStatsResponse.builder()
			.status(alarmStatusStats.getStatus())
			.count(alarmStatusStats.getCount())
			.build();
	}
}
