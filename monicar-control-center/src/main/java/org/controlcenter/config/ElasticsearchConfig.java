package org.controlcenter.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "org.controlcenter.vehicle.infrastructure.elasticsearch.repository")
public class ElasticsearchConfig {
}