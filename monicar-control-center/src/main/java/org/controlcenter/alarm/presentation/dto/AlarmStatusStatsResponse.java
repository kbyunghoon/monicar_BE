package org.controlcenter.alarm.presentation.dto;

import org.controlcenter.alarm.domain.AlarmStatusStats;

import lombok.Builder;

@Builder
public record AlarmStatusStatsResponse(
	String name,
	long count
) {
	public static AlarmStatusStatsResponse from(AlarmStatusStats alarmStatusStats) {
		return AlarmStatusStatsResponse.builder()
			.name(String.valueOf(alarmStatusStats.getName()))
			.count(alarmStatusStats.getCount())
			.build();
	}
}
