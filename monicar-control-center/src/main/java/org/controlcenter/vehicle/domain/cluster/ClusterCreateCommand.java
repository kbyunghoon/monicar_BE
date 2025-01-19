package org.controlcenter.vehicle.domain.cluster;

import org.controlcenter.common.exception.BusinessException;
import org.controlcenter.common.response.code.ErrorCode;

import lombok.Getter;

@Getter
public class ClusterCreateCommand {
	private static final int MIN_ZOOM_LEVEL = 1;
	private static final int MAX_ZOOM_LEVEL = 14;

	private final Integer zoomLevel;
	private final GeoCoordinate ne;
	private final GeoCoordinate sw;

	private ClusterCreateCommand(Integer zoomLevel, GeoCoordinate ne, GeoCoordinate sw) {
		this.zoomLevel = zoomLevel;
		this.ne = ne;
		this.sw = sw;
	}

	public static ClusterCreateCommand of(
		Integer zoomLevel,
		Integer neLat,
		Integer neLng,
		Integer swLat,
		Integer swLng
	) {
		validateZoomLevel(zoomLevel);

		return new ClusterCreateCommand(
			zoomLevel,
			GeoCoordinate.of(neLat, neLng),
			GeoCoordinate.of(swLat, swLng)
		);
	}

	private static void validateZoomLevel(Integer zoomLevel) {
		if (zoomLevel < MIN_ZOOM_LEVEL || MAX_ZOOM_LEVEL < zoomLevel) {
			System.out.println("test");
			throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE);
		}
	}
}
