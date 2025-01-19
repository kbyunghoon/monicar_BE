package org.controlcenter.vehicle.domain.cluster;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("[domain 단위테스트] CoordinateDivider")
class CoordinateDividerTest {

	@DisplayName("범위에 따라 클러스터링을 위한 좌표 그리드를 생성할 수 있다.")
	@Test
	void generateGridCoordinates() {
		// given
		GeoCoordinate ne = new GeoCoordinate(11, 11);
		GeoCoordinate sw = new GeoCoordinate(1, 1);
		int rows = 4;
		int cols = 4;

		// when
		GeoCoordinate[][] result = CoordinateDivider.generateGridCoordinates(ne, sw, rows, cols);

		// then
		assertThat(result.length).isEqualTo(5);
		assertThat(result[1].length).isEqualTo(5);
	}
}