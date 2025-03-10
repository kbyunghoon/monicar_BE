package org.controlcenter.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {
	@Value("${swagger.server.prod-url}")
	private String prodUrl;

	@Value("${swagger.server.dev-url}")
	private String devUrl;

	@Bean
	public OpenAPI openAPI(Environment env) {
		Info info = new Info()
			.title("Monicar API")
			.description("API")
			.version("1.0.0");

		return new OpenAPI()
			.info(info)
			.servers(getServers(env));
	}

	private List<Server> getServers(Environment env) {
		if (Arrays.asList(env.getActiveProfiles()).contains("prod")) {
			return List.of(
				new Server()
					.url(prodUrl)
					.description("개발 서버")
			);
		} else {
			return List.of(
				new Server()
					.url(devUrl)
					.description("로컬 서버"),
				new Server()
					.url(prodUrl)
					.description("개발 서버")
			);
		}
	}
}