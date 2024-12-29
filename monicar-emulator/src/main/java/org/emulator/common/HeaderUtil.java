package org.emulator.common;

import java.util.function.Consumer;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class HeaderUtil {
	private final static String CACHE_CONTROL_VALUE = "no-cache";
	private final static String ACCEPT_ENCODING_VALUE = "gzip,deflate";
	private final static String KEY_VERSION_NAME = "Key-Version";
	private final static String KEY_VERSION_VALUE = "1.0";

	public static Consumer<HttpHeaders> defaultHeaders() {
		return headers -> {
			headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
			headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
			headers.add(HttpHeaders.CACHE_CONTROL, CACHE_CONTROL_VALUE);
			headers.add(HttpHeaders.ACCEPT_ENCODING, ACCEPT_ENCODING_VALUE);
			headers.add(KEY_VERSION_NAME, KEY_VERSION_VALUE);
		};
	}
}
