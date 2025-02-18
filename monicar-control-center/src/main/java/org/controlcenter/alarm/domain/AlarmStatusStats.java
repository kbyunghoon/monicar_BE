package org.controlcenter.alarm.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AlarmStatusStats {
	private Long required;
	private Long scheduled;
	private Long inProgress;
	private Long completed;
}
