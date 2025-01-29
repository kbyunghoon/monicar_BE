package org.emulator.device.application.port;

/**
 * 차량 정보를 내부적으로 저장하고 변경하는 역할을 선언한다.
 */
public interface EmulatorRepository {
	int startLogDistance();

	int getCurrentDistance();

	int updateCurrentDistance(int intervalDistance);

	boolean isTurnOn();

	void turnOn();

	void turnOff();
}
