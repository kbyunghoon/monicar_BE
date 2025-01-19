package org.controlcenter.vehicle.domain.cluster;

import lombok.Getter;

@Getter
public class GeoClustering {
	private final GeoCoordinate coordinate;
	private final int count;

	public GeoClustering(GeoCoordinate coordinate, int count) {
		this.coordinate = coordinate;
		this.count = count;
	}
}
