package org.controlcenter.alarm.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SendAlarm {
	private long id;
	private String vehicleNumber;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String managerName;
	private AlarmStatus status;

	public static SendAlarm of(
		long id, String vehicleNumber, String managerName, AlarmStatus status
	) {
		return SendAlarm.builder()
			.id(id)
			.vehicleNumber(vehicleNumber)
			.managerName(managerName)
			.status(status)
			.build();
	}

	public static SendAlarm of(
		long id, String vehicleNumber, AlarmStatus status
	) {
		return SendAlarm.builder()
			.id(id)
			.vehicleNumber(vehicleNumber)
			.status(status)
			.build();
	}
}