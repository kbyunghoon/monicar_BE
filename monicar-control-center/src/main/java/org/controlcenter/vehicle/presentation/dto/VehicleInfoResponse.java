package org.controlcenter.vehicle.presentation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;

import lombok.Builder;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Builder
@Slf4j
public record VehicleInfoResponse(
	Long vehicleId,
	String vehicleNumber,
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime firstDateAt,
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime lastDateAt
) {
	public VehicleInfoResponse(Long vehicleId, String vehicleNumber,
		LocalDateTime firstDateAt,
		LocalDateTime lastDateAt) {
		this.vehicleId = vehicleId;
		this.vehicleNumber = vehicleNumber;
		this.firstDateAt = normalizeToMinute(firstDateAt);
		this.lastDateAt = roundUpToNextMinute(lastDateAt);
	}

	private static LocalDateTime normalizeToMinute(LocalDateTime dateTime) {
		return dateTime.withSecond(0);
	}

	private static LocalDateTime roundUpToNextMinute(LocalDateTime dateTime) {
		return dateTime.getSecond() == 0 ?
			dateTime : dateTime.plusMinutes(1).withSecond(0);
	}
}
