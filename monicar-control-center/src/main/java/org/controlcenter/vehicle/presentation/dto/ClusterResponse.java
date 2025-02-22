package org.controlcenter.vehicle.presentation.dto;

import org.controlcenter.vehicle.domain.Cluster;

import lombok.Builder;

@Builder
public record ClusterResponse(
	double lat,
	double lng,
	long count
) {
	public static ClusterResponse from(Cluster cluster) {
		return ClusterResponse.builder()
			.lat(cluster.getLat())
			.lng(cluster.getLng())
			.count(cluster.getCount())
			.build();
	}
}