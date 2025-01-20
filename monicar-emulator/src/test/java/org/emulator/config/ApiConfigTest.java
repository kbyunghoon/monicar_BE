package org.emulator.config;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@DisplayName("application.yml 환경 변수 확인 테스트")
@SpringBootTest
class ApiConfigTest {

	@Value("${api.base-url}")
	private String baseUrl;

	@Autowired
	private ApiConfig apiConfig;

	@DisplayName("@Value가 application.yml 환경 변수를 잘 갖고오는지")
	@Test
	void testYmlValues() {
		String expectedBaseUrl = "http://localhost:8082/api/v1/event-hub/";
		assertEquals(expectedBaseUrl, baseUrl);
	}

	@DisplayName("ApiConfig가 application.yml 환경 변수를 잘 갖고오는지")
	@Test
	void testApiConfig() {
		String expectedBaseUrl = "http://localhost:8082/api/v1/event-hub/";
		assertEquals(expectedBaseUrl, apiConfig.getBaseUrl());
	}
}