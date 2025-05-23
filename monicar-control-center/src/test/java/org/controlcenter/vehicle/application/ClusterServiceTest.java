package org.controlcenter.vehicle.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.controlcenter.common.exception.BusinessException;
import org.controlcenter.vehicle.domain.Cluster;
import org.controlcenter.vehicle.domain.ClusterDetail;
import org.controlcenter.vehicle.domain.VehicleStatus;
import org.controlcenter.vehicle.infrastructure.jpa.VehicleInformationJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("[service 단위 테스트] ClusterService")
class ClusterServiceTest {
	private VehicleInformationJpaRepository vehicleInformationJpaRepository;
	private ClusterService clusterService;

	@BeforeEach
	void setUp() {
		vehicleInformationJpaRepository = mock(VehicleInformationJpaRepository.class);
		clusterService = new ClusterService(vehicleInformationJpaRepository);
	}

	@DisplayName("클러스터 정보 조회 테스트")
	@Test
	void getClusters_success() {
		// Given
		int neLat = 1270000, neLng = 370000, swLat = 1260000, swLng = 360000;
		int zoomLevel = 10;
		VehicleStatus status = VehicleStatus.IN_OPERATION;

		List<Cluster> expected = List.of(mock(Cluster.class));
		when(vehicleInformationJpaRepository.findClusters(anyInt(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt(),
			eq(status)))
			.thenReturn(expected);

		// When
		List<Cluster> result = clusterService.getClusters(neLat, neLng, swLat, swLng, zoomLevel, status);

		// Then
		assertThat(result).isEqualTo(expected);
	}

	@DisplayName("줌 레벨이 유효하지 않을 경우 예외 테스트")
	@Test
	void getClusters_invalidZoomLevel() {
		// Given
		int neLat = 1270000, neLng = 370000, swLat = 1260000, swLng = 360000;
		int zoomLevel = 0;
		VehicleStatus status = VehicleStatus.IN_OPERATION;

		// When & Then
		assertThrows(BusinessException.class, () ->
				clusterService.getClusters(neLat, neLng, swLat, swLng, zoomLevel, status),
			"줌 레벨이 유효하지 않을 경우 예외 발생"
		);
	}

	@DisplayName("클러스터 상세 정보 조회 테스트")
	@Test
	void getClustersDetail_success() {
		// Given
		int neLat = 1270000, neLng = 370000, swLat = 1260000, swLng = 360000;
		VehicleStatus status = VehicleStatus.NOT_DRIVEN;

		List<ClusterDetail> expected = List.of(mock(ClusterDetail.class));
		when(vehicleInformationJpaRepository.findClustersDetail(swLat, neLat, swLng, neLng, status))
			.thenReturn(expected);

		// When
		List<ClusterDetail> result = clusterService.getClustersDetail(neLat, neLng, swLat, swLng, status);

		// Then
		assertThat(result).isEqualTo(expected);
	}
}