package org.controlcenter.alarm.presentation.dto;

import org.controlcenter.alarm.domain.AlarmInfo;
import org.controlcenter.alarm.domain.AlarmStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;

@Builder
public record AlarmResponse(
	long id,
	String vehicleNumber,
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String managerName,
	AlarmStatus status
) {
	public static AlarmResponse from(AlarmInfo alarmInfo) {
		return AlarmResponse.builder()
			.id(alarmInfo.getId())
			.vehicleNumber(alarmInfo.getVehicleNumber())
			.managerName(alarmInfo.getManagerName())
			.status(alarmInfo.getStatus())
			.build();
	}
}
