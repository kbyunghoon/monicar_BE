package org.controlcenter.vehicle.presentation.dto;

import org.controlcenter.vehicle.domain.cluster.GeoCoordinateDetails;

public record GeoCoordinateDetailsResponse(
	Long vehicleId,
	String vehicleNumber,
	Integer lat,
	Integer lng
) {

	public static GeoCoordinateDetailsResponse from(GeoCoordinateDetails geoCoordinateDetails) {
		return new GeoCoordinateDetailsResponse(
			geoCoordinateDetails.getVehicleId(),
			geoCoordinateDetails.getVehicleNumber(),
			geoCoordinateDetails.getLat(),
			geoCoordinateDetails.getLng()
		);
	}
}
