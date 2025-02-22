package org.controlcenter.vehicle.presentation.dto;

import org.controlcenter.vehicle.domain.cluster.GeoClustering;
import org.controlcenter.vehicle.domain.cluster.GeoCoordinate;

public record GeoClusteringResponse(
	GeoCoordinate coordinate,
	int count
) {
	public static GeoClusteringResponse from(GeoClustering geoClustering) {
		return new GeoClusteringResponse(geoClustering.getCoordinate(), geoClustering.getCount());
	}
}
