package org.eventhub.infrastructure.external.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@RequiredArgsConstructor
@Configuration
public class RestClientFactory {
	private final HeaderUtils headerUtils;
	@Value("${api.base-url}")
	private String baseUrl;

	@Bean
	public RestClient restClient() {
		return RestClient.builder()
			.baseUrl(baseUrl)
			.defaultHeaders(headerUtils.defaultHeaders())
			.build();
	}

	@Bean
	public RestClientService restClientService(RestClient restClient) {
		return new RestClientService(restClient, new ObjectMapper(), headerUtils);
	}
}
