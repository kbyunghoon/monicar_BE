package org.eventhub.infrastructure.external.util;


import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import lombok.extern.slf4j.Slf4j;

import org.eventhub.common.exception.BusinessException;
import org.eventhub.common.response.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public final class HeaderUtils {
	private static final String CACHE_CONTROL_VALUE = "no-cache";
	private static final String ACCEPT_ENCODING_VALUE = "identity";
	private static final String KEY_VERSION_VALUE = "1.0";

	@Value("${api.key}")
	private String apiKey;

	public Consumer<HttpHeaders> defaultHeaders() {
		return headers -> {
			headers.add(HeaderName.CONTENT_TYPE.getName(), MediaType.APPLICATION_JSON_VALUE);
			headers.add(HeaderName.ACCEPT.getName(), MediaType.APPLICATION_JSON_VALUE);
			headers.add(HeaderName.CACHE_CONTROL.getName(), CACHE_CONTROL_VALUE);
			headers.add(HeaderName.ACCEPT_ENCODING.getName(), ACCEPT_ENCODING_VALUE);
			headers.add(HeaderName.KEY_VERSION.getName(), KEY_VERSION_VALUE);
		};
	}

	public Map<String, String> additionalHeaders(HeaderName... headerNames) {
		Map<String, String> headers = new HashMap<>();

		for (HeaderName headerName : headerNames) {
			switch (headerName) {
				case X_API_KEY -> headers.put(headerName.getName(), apiKey);

				default -> throw new BusinessException(ErrorCode.UNSUPPORTED_HEADER);
			}
		}

		return headers;
	}
}
