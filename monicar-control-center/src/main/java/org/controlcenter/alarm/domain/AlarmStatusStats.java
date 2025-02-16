package org.controlcenter.alarm.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AlarmStatusStats {
	private AlarmStatus name;  // "점검요구", "점검예정", ...
	private long count;
}
