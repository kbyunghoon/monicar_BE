package org.emulator.device.domain;

import lombok.Getter;

/**
 * 에뮬레이터 전역 데이터를 관리하는 클래스
 *
 * @field vehicleOffInfoList  시동 OFF 시간 이력 목록
 */
@Getter
public class VehicleInfo {
	private int totalDistance;

	public VehicleInfo() {
		totalDistance = 0;
	}

	public int updateTotalDistance(int distance) {
		totalDistance = distance;
		return totalDistance;
	}
}
