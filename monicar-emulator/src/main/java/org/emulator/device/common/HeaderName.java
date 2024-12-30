package org.emulator.device.common;

import org.springframework.http.HttpHeaders;

public enum HeaderName {
	CONTENT_TYPE(HttpHeaders.CONTENT_TYPE),
	ACCEPT(HttpHeaders.ACCEPT),
	CACHE_CONTROL(HttpHeaders.CACHE_CONTROL),
	ACCEPT_ENCODING(HttpHeaders.ACCEPT_ENCODING),
	KEY_VERSION("Key-Version"),
	TIMESTAMP("Timestamp"),
	TUID("TUID"),
	TOKEN("Token");

	private final String name;

	HeaderName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
