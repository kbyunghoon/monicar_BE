package org.emulator.infrastructure.external;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.emulator.common.HeaderUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientFactory {

	@Bean
	public Map<UrlPathEnum, RestClient> restClients() {
		Map<UrlPathEnum, RestClient> restClients = new HashMap<>();

		Arrays.stream(UrlPathEnum.values()).forEach(apiUrl -> {
			restClients.put(apiUrl, RestClient.builder()
				.baseUrl(apiUrl.getApiUrl())
				.defaultHeaders(HeaderUtil.defaultHeaders())
				.build());
		});

		for (UrlPathEnum apiUrl : UrlPathEnum.values()) {
			restClients.put(apiUrl, RestClient.builder()
				.baseUrl(apiUrl.getApiUrl())
				.defaultHeaders(HeaderUtil.defaultHeaders())
				.build());
		}

		return restClients;
	}

	@Bean
	public RestClientService restClientService(Map<UrlPathEnum, RestClient> restClients) {
		return new RestClientService(restClients, new ObjectMapper());
	}
}
