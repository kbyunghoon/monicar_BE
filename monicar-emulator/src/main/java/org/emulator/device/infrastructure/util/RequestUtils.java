package org.emulator.device.infrastructure.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.emulator.device.common.exception.BusinessException;
import org.emulator.device.common.response.ErrorCode;

public final class RequestUtils {
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

	public static String getCurrentTimestamp() {
		return LocalDateTime.now().format(DATE_TIME_FORMATTER);
	}

	public static String generateTUID() {
		return UUID.randomUUID().toString();
	}

	private RequestUtils() {
		throw new BusinessException(ErrorCode.ILLEGAL_UTILITY_CLASS_ACCESS);
	}
}
