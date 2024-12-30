package org.emulator.device.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public final class RequestUtils {
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

	public static String getCurrentTimestamp() {
		return LocalDateTime.now().format(DATE_TIME_FORMATTER);
	}

	public static String generateTUID() {
		return UUID.randomUUID().toString();
	}
}
