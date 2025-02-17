package org.controlcenter.alarm.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AlarmStatusStats {
	private AlarmStatus status;
	private long count;
}
