package org.eventhub.infrastructure.external.util;

import org.springframework.http.HttpHeaders;

public enum HeaderName {
	CONTENT_TYPE(HttpHeaders.CONTENT_TYPE),
	ACCEPT(HttpHeaders.ACCEPT),
	CACHE_CONTROL(HttpHeaders.CACHE_CONTROL),
	ACCEPT_ENCODING(HttpHeaders.ACCEPT_ENCODING),
	KEY_VERSION("Key-Version"),
	X_API_KEY("X-API-KEY");

	private final String name;

	HeaderName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
