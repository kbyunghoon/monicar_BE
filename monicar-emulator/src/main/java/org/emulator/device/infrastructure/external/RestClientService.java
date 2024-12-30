package org.emulator.device.infrastructure.external;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RestClientService {
	private final Map<UrlPathEnum, RestClient> restClients;
	private final ObjectMapper objectMapper;

	public RestClient getRestClient(UrlPathEnum path) {
		return restClients.get(path);
	}

	public <T> T post(
		RestClient restClient,
		Object body,
		Map<String, String> headers,
		Class<T> responseType
	) {
		return restClient.post()
			.headers(header -> headers.forEach(header::add))
			.body(body)
			.exchange((request, response) -> {
				String responseBody = response.getBody().toString();
				return objectMapper.readValue(responseBody, responseType);
			});
	}
}
