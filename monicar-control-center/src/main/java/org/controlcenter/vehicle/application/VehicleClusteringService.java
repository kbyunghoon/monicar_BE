package org.controlcenter.vehicle.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.controlcenter.common.exception.BusinessException;
import org.controlcenter.common.response.code.ErrorCode;
import org.controlcenter.vehicle.application.port.VehicleRepository;
import org.controlcenter.vehicle.domain.cluster.ClusterCreateCommand;
import org.controlcenter.vehicle.domain.cluster.CoordinateDivider;
import org.controlcenter.vehicle.domain.cluster.GeoClustering;
import org.controlcenter.vehicle.domain.cluster.GeoCoordinate;
import org.controlcenter.vehicle.domain.cluster.GeoCoordinateDetails;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

record GridSize(
	int row,
	int col
) {
}

@RequiredArgsConstructor
@Service
public class VehicleClusteringService {
	private static final int CLUSTERING_COUNT_THRESHOLD = 10;
	private static final Map<Integer, GridSize> zoomLevelTable = Map.of(
		1, new GridSize(2, 2),
		3, new GridSize(3, 3),
		5, new GridSize(4, 4),
		7, new GridSize(5, 5),
		9, new GridSize(6, 6),
		11, new GridSize(7, 7),
		13, new GridSize(8, 8),
		14, new GridSize(9, 9)
	);

	private final VehicleRepository vehicleRepository;

	public List<GeoClustering> clusterCoordinate(ClusterCreateCommand command, Long companyId) {
		List<GeoCoordinate> geoCoordinates = vehicleRepository.findCoordinatesByCompanyId(command, companyId);

		GeoCoordinate ne = command.getNe(); // 우상단
		GeoCoordinate sw = command.getSw(); // 좌하단
		int zoomLevel = command.getZoomLevel();

		GridSize gridSize = zoomLevelTable.get(zoomLevel);
		int row = gridSize.row();
		int col = gridSize.col();

		List<GeoClustering> geoClusterings = new ArrayList<>();
		GeoCoordinate[][] gridCoordinates = CoordinateDivider.generateGridCoordinates(ne, sw, row, col);
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				var boundsNe = gridCoordinates[i + 1][j + 1]; // 우상단
				var boundsSw = gridCoordinates[i][j];         // 좌하단

				List<GeoCoordinate> filtered = geoCoordinates.stream()
					.filter(coordinate -> coordinate.isInBounds(boundsSw, boundsNe))
					.toList();

				int sumLat = filtered.stream()
					.mapToInt(GeoCoordinate::getLat)
					.sum();
				int sumLng = filtered.stream()
					.mapToInt(GeoCoordinate::getLng)
					.sum();

				int count = filtered.size();
				if (count == 0) {
					GeoCoordinate mid = null;
					geoClusterings.add(new GeoClustering(mid, count));
					continue;
				}
				GeoCoordinate mid = new GeoCoordinate(sumLat / count, sumLng / count);
				geoClusterings.add(new GeoClustering(mid, count));
			}
		}

		return geoClusterings;
	}

	public List<GeoCoordinateDetails> clusterCoordinateDetail(ClusterCreateCommand command, Long companyId) {
		List<GeoCoordinateDetails> geoCoordinateDetails = vehicleRepository.findCoordinatesDetailsByCompanyId(command,
			companyId);
		validateClusteringNumOfVehicle(geoCoordinateDetails);

		return geoCoordinateDetails;
	}

	private void validateClusteringNumOfVehicle(List<GeoCoordinateDetails> geoCoordinateDetails) {
		if (CLUSTERING_COUNT_THRESHOLD < geoCoordinateDetails.size()) {
			throw new BusinessException("클러스터링 차량 개수가 너무 많습니다.", ErrorCode.INTERNAL_SERVER_ERROR);
		}
	}
}
