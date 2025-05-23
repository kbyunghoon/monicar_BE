package org.emulator.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
public class ApiConfig {
	@Value("${api.base-url}")
	private String baseUrl;
}

