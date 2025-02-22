package org.controlcenter.alarm.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SendAlarm {
	private long id;
	private String vehicleNumber;
	private AlarmStatus status;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer drivingDistance;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String managerName;
}