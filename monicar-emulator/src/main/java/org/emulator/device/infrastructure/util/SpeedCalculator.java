package org.emulator.device.infrastructure.util;

import org.emulator.device.domain.CycleInfo;
import org.emulator.device.infrastructure.GpsTime;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class SpeedCalculator implements Calculator {
	private final Calculator distanceCalculator = new DistanceCalculator();
	private static final int SPEED_MAX = 255;
	private static final int SPEED_MIN = 0;
	private static final double MILLIS_TO_SECONDS_SCALE = 1000.0;
	/**
	 * 스케일링을 위한 최대 예상 속도 (자동차의 최고 속도인 약 70 m/s -> 252 km/h)
	 * @value 70.0
	 */
	public static final double EXPECTED_MAX_SPEED_MPS = 70.0;

	/**
	 * 이동 거리와 시간 차이를 기반으로 속도를 계산하고, 이를 0 ~ 255 범위로 스케일링합니다.
	 * @field  distance 이동 거리 (미터)
	 * @field  timeMillis    시간 차이 (밀리 초)
	 * @return 스케일된 속도 (0 ~ 255)
	 */
	@Override
	public Integer calculate(CycleInfo preInfo, GpsTime curInfo) {
		int distance = distanceCalculator.calculate(preInfo, curInfo);
		long timeMillis = Duration.between(preInfo.getIntervalAt(), curInfo.intervalAt()).toMillis();

		if (timeMillis <= 0L) {
			return 0;
		}
		double timeSeconds = timeMillis / MILLIS_TO_SECONDS_SCALE;
		double speedMps = distance / timeSeconds;

		int speed = (int)((speedMps / EXPECTED_MAX_SPEED_MPS) * SPEED_MAX);
		return Math.clamp(speed, SPEED_MIN, SPEED_MAX);
	}
}
