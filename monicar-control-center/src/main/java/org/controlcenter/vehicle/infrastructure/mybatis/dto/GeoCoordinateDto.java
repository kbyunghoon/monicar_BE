package org.controlcenter.vehicle.infrastructure.mybatis.dto;

import org.controlcenter.vehicle.domain.cluster.GeoCoordinate;

public class GeoCoordinateDto {
	private final Integer lat;
	private final Integer lng;

	public GeoCoordinateDto(Integer lat, Integer lng) {
		this.lat = lat;
		this.lng = lng;
	}

	public GeoCoordinate toDomain() {
		return new GeoCoordinate(lat, lng);
	}
}
