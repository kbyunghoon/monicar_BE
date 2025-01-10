package org.controlcenter.vehicle.domain;

import lombok.Getter;
import org.controlcenter.common.exception.BusinessException;
import org.controlcenter.common.response.code.ErrorCode;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class VehicleEventCreate {
	private static final int ON_OFF_TIME_LENGTH = 14; // ccyyMMddHHmmss

	private final long vehicleId;
	private final VehicleEventType eventType;
	private final LocalDateTime eventAt;

	private VehicleEventCreate() {
		this.vehicleId = 0;
		this.eventType = null;
		this.eventAt = null;
	}

	private VehicleEventCreate(
			final long vehicleId,
			final VehicleEventType eventType,
			final LocalDateTime eventAt) {
		this.vehicleId = vehicleId;
		this.eventType = eventType;
		this.eventAt = eventAt;
	}

	public static VehicleEventCreate of(
			final long vehicleId,
			final VehicleEventType onEvent,
			final String rawEventAt) {
		LocalDateTime onTime = validateEventAt(rawEventAt);

		return new VehicleEventCreate(
				vehicleId,
				onEvent,
				onTime
		);
	}

	private static LocalDateTime validateEventAt(String rawEventAt) {
		if (rawEventAt.length() != ON_OFF_TIME_LENGTH || !rawEventAt.chars().allMatch(Character::isDigit)) {
			throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE);
		}

		DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		return LocalDateTime.parse(rawEventAt, dateTimeFormat);
	}
}
