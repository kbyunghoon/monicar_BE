package org.emulator.device.infrastructure.entity;

import java.util.concurrent.atomic.AtomicInteger;

import lombok.Getter;

/**
 * 에뮬레이터 전역 데이터를 관리하는 클래스
 *
 * @field isOn  시동 On/Off 플래그
 */
public class VehicleEntity {
	@Getter
	private boolean isOn;
	private AtomicInteger totalDistance;
	private AtomicInteger currentDistance;

	public VehicleEntity() {
		isOn = false;
		totalDistance = new AtomicInteger(0);
		currentDistance = new AtomicInteger(0);
	}

	public int resetCurrentDistance() {
		return currentDistance.getAndSet(0);
	}

	public int getCurrentDistance() {
		return currentDistance.get();
	}

	public int addCurrentDistance(int distance) {
		if (isOn) {
			return currentDistance.addAndGet(distance);
		}
		return currentDistance.get();
	}

	public int getTotalDistance() {
		return totalDistance.get();
	}

	public int addFromOnToOffDistance() {
		if (!isOn) {
			return totalDistance.addAndGet(currentDistance.get());
		}
		return totalDistance.get();
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
