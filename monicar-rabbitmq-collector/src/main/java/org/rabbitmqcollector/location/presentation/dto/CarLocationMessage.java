package org.rabbitmqcollector.location.presentation.dto;

import lombok.Builder;

@Builder
public record CarLocationMessage(
	Long id,
	int lat,
	int lng,
	long timestamp
) {
}
