package org.emulator.device.infrastructure.entity;

import lombok.Getter;

/**
 * 에뮬레이터 전역 데이터를 관리하는 클래스
 *
 * @field isOn  시동 On/Off 플래그
 */
@Getter
public class VehicleEntity {
	private boolean isOn;
	private int totalDistance;
	private int currentDistance = 0;

	public VehicleEntity() {
		isOn = false;
		totalDistance = 0;
	}

	public int resetCurrentDistance() {
		currentDistance = 0;
		return currentDistance;
	}

	public int addCurrentDistance(int distance) {
		if (isOn) {
			this.currentDistance += distance;
		}
		return currentDistance;
	}

	public void turnOn() {
		if (!isOn) {
			isOn = true;
		}
	}

	public void turnOff() {
		if (isOn) {
			isOn = false;
		}
	}
}
