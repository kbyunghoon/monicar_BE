package org.emulator.device.infrastructure.util;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public final class HeaderUtils {
	private static final String CACHE_CONTROL_VALUE = "no-cache";
	private static final String ACCEPT_ENCODING_VALUE = "gzip, deflate";
	private static final String KEY_VERSION_VALUE = "1.0";

	public static Consumer<HttpHeaders> defaultHeaders() {
		return headers -> {
			headers.add(HeaderName.CONTENT_TYPE.getName(), MediaType.APPLICATION_JSON_VALUE);
			headers.add(HeaderName.ACCEPT.getName(), MediaType.APPLICATION_JSON_VALUE);
			headers.add(HeaderName.CACHE_CONTROL.getName(), CACHE_CONTROL_VALUE);
			headers.add(HeaderName.ACCEPT_ENCODING.getName(), ACCEPT_ENCODING_VALUE);
			headers.add(HeaderName.KEY_VERSION.getName(), KEY_VERSION_VALUE);
		};
	}

	public static Map<String, String> additionalHeaders(HeaderName... headerNames) {
		Map<String, String> headers = new HashMap<>();

		for (HeaderName headerName : headerNames) {
			switch (headerName) {
				case TIMESTAMP -> headers.put(headerName.getName(), RequestUtils.getCurrentTimestamp());
				case TUID -> headers.put(headerName.getName(), RequestUtils.generateTUID());
				case TOKEN -> headers.put(headerName.getName(), "my-vehicle-token");

				default -> throw new IllegalArgumentException("Unhandled header: " + headerName);
			}
		}

		return headers;
	}

	private HeaderUtils() {
		throw new IllegalStateException("Utility class");
	}
}
