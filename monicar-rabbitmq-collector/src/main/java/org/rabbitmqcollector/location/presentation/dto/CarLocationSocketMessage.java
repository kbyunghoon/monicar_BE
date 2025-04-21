package org.rabbitmqcollector.location.presentation.dto;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.rabbitmqcollector.location.domain.CycleInfo;
import org.rabbitmqcollector.location.domain.GpsStatus;

import lombok.Builder;

@Builder
public record CarLocationSocketMessage(
	Long id,
	String vehicleNumber,
	int lat,
	int lng,
	long timestamp
) {
	public static CycleInfo toDomain(CarLocationSocketMessage carLocationMessage) {
		return CycleInfo.builder()
			.vehicleId(carLocationMessage.id)
			.status(GpsStatus.A)
			.lat(carLocationMessage.lat)
			.lng(carLocationMessage.lng)
			.ang(0)
			.spd(80)
			.intervalAt(
				LocalDateTime.ofInstant(Instant.ofEpochMilli(carLocationMessage.timestamp), ZoneOffset.UTC)
					.withNano(0))
			.build();
	}
}
