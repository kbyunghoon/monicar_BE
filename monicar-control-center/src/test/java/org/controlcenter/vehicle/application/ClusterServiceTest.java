package org.controlcenter.vehicle.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.controlcenter.vehicle.domain.Cluster;
import org.controlcenter.vehicle.domain.VehicleStatus;
import org.controlcenter.vehicle.infrastructure.jpa.VehicleInformationJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("[service 통합테스트] ClusterService")
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
}