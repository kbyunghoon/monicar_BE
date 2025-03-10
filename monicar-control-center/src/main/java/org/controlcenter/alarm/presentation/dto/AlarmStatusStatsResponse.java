package org.controlcenter.alarm.presentation.dto;

import org.controlcenter.alarm.domain.AlarmStatusStats;

import lombok.Builder;

@Builder
public record AlarmStatusStatsResponse(
	Long required,
	Long scheduled,
	Long inProgress,
	Long completed
) {
	public static AlarmStatusStatsResponse from(AlarmStatusStats alarmStatusStats) {
		return AlarmStatusStatsResponse.builder()
			.required(alarmStatusStats.getRequired())
			.scheduled(alarmStatusStats.getScheduled())
			.inProgress(alarmStatusStats.getInProgress())
			.completed(alarmStatusStats.getCompleted())
			.build();
	}
}
