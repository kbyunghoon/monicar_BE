package org.eventhub.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.Getter;

import lombok.extern.slf4j.Slf4j;

import org.eventhub.common.exception.BusinessException;
import org.eventhub.common.response.ErrorCode;

@Getter
@Slf4j
public class VehicleOnEventCreate extends VehicleEventCreateAbs {
	private static final int EXPECTED_ON_OFF_TIME_LENGTH = 14; // ccyyMMddHHmmss
	private static final String DATE_TIME_PATTERN = "yyyyMMddHHmmss";

	private VehicleOnEventCreate(
			final long vehicleId,
			final VehicleEventType eventType,
			final LocalDateTime eventAt) {
		super(vehicleId, eventType, eventAt);
	}

	public static VehicleOnEventCreate of(
			final long existedVehicleId,
			final long existedSum,
			final VehicleEventType onEvent,
			final String rawEventAt,
			final int sum) {
		LocalDateTime onTime = validateEventAt(rawEventAt);
		validateSum(existedSum, sum);

		return new VehicleOnEventCreate(
				existedVehicleId,
				onEvent,
				onTime
		);
	}

	private static void validateSum(final long existedSum, final long sum) {
		if (existedSum != sum) {
			log.info("existedSum: {}, sum: {}", existedSum, sum);
			throw new BusinessException(ErrorCode.TOTALDISTANCE_INCONSISTENCY);
		}
	}

	private static LocalDateTime validateEventAt(final String rawEventAt) {
		validateFormat(rawEventAt);

		final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

		return LocalDateTime.parse(rawEventAt, dateTimeFormat);
	}

	private static void validateFormat(final String rawEventAt) {
		if (!isValidLength(rawEventAt) || !isNumeric(rawEventAt)) {
			throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE);
		}
	}

	private static boolean isValidLength(final String rawEventAt) {
		return rawEventAt.length() == EXPECTED_ON_OFF_TIME_LENGTH;
	}

	private static boolean isNumeric(final String rawEventAt) {
		return rawEventAt.chars().allMatch(Character::isDigit);
	}
}
