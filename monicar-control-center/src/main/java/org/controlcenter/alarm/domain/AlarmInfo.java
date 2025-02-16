package org.controlcenter.alarm.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AlarmInfo {
	private long id;
	private String vehicleNumber;
	private String managerName;
	AlarmStatus status;
}
