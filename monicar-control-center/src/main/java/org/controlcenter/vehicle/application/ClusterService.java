package org.controlcenter.vehicle.application;

import java.util.List;

import org.controlcenter.common.exception.BusinessException;
import org.controlcenter.common.response.code.ErrorCode;
import org.controlcenter.vehicle.domain.Cluster;
import org.controlcenter.vehicle.domain.VehicleStatus;
import org.controlcenter.vehicle.infrastructure.jpa.VehicleInformationJpaRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClusterService {
	private final VehicleInformationJpaRepository vehicleInformationJpaRepository;

	private static final int MIN_ZOOM = 1;
	private static final int MAX_ZOOM = 13;

	public List<Cluster> getClusters(int neLat, int neLng, int swLat, int swLng, int zoomLevel, VehicleStatus status) {
		if (zoomLevel < MIN_ZOOM || zoomLevel > MAX_ZOOM) {
			throw new BusinessException(ErrorCode.INVALID_ZOOM_LEVEL);
		}

		int latRange = neLat - swLat;
		int lngRange = neLng - swLng;

		double minFactor = 0.1;
		double maxFactor = 0.5;
		double factor = minFactor + (maxFactor - minFactor) * (zoomLevel - MIN_ZOOM) / (MAX_ZOOM - MIN_ZOOM);
		int gridLat = (int)(latRange * factor);
		int gridLng = (int)(lngRange * factor);

		return vehicleInformationJpaRepository.findClusters(swLat, neLat, swLng, neLng, gridLat, gridLng, status);
	}
}