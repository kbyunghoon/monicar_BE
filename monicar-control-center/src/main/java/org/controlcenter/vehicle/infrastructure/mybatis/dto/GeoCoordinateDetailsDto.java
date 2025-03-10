package org.controlcenter.vehicle.infrastructure.mybatis.dto;

import org.controlcenter.vehicle.domain.cluster.GeoCoordinateDetails;

public class GeoCoordinateDetailsDto {
	private final Long vehicleId;
	private final String vehicleNumber;
	private final Integer lat;
	private final Integer lng;

	public GeoCoordinateDetailsDto(Long vehicleId, String vehicleNumber, Integer lat, Integer lng) {
		this.vehicleId = vehicleId;
		this.vehicleNumber = vehicleNumber;
		this.lat = lat;
		this.lng = lng;
	}

	public GeoCoordinateDetails toDomain() {
		return new GeoCoordinateDetails(vehicleId, vehicleNumber, lat, lng);
	}
}
