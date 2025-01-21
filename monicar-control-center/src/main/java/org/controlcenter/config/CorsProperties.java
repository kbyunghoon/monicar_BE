package org.controlcenter.config;

import java.util.List;

import lombok.Getter;

import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "cors")
public class CorsProperties {
	private List<String> allowedOrigins;
}