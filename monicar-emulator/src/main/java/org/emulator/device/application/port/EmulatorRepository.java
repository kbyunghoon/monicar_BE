package org.emulator.device.application.port;

/**
 * 차량 정보 저장 역할
 */
public interface EmulatorRepository {
	int startLogDistance();

	int getCurrentDistance();

	int updateCurrentDistance(int intervalDistance);

	boolean isTurnOn();

	void turnOn();

	void turnOff();
}
