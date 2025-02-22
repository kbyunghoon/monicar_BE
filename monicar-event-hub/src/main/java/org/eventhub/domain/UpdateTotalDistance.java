package org.eventhub.domain;

import lombok.Builder;

@Builder
public record UpdateTotalDistance (
	Long vehicleId,
	Integer additionalDistance
) {
	public static UpdateTotalDistance of(Long vehicleId, Integer additionalDistance) {
		return UpdateTotalDistance.builder()
			.vehicleId(vehicleId)
			.additionalDistance(additionalDistance)
			.build();
	}
}
