package org.controlcenter.vehicle.domain.cluster;

import lombok.Getter;

@Getter
public class GeoCoordinateDetails {
	private final Long vehicleId;
	private final String vehicleNumber;
	private final Integer lat;
	private final Integer lng;

	public GeoCoordinateDetails(Long vehicleId, String vehicleNumber, Integer lat, Integer lng) {
		this.vehicleId = vehicleId;
		this.vehicleNumber = vehicleNumber;
		this.lat = lat;
		this.lng = lng;
	}
}
