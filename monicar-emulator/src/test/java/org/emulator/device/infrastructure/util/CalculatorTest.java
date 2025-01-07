package org.emulator.device.infrastructure.util;

import org.emulator.device.domain.CycleInfo;
import org.emulator.device.infrastructure.GpsTime;
import org.emulator.device.infrastructure.external.command.vo.Geo;
import org.emulator.pipe.Gps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("에뮬레이터 연산 테스트")
class CalculatorTest {
	Calculator calculator;
	LocalDateTime now;
	CycleInfo start;
	GpsTime end;

	@BeforeEach
	void setUp() {
		// given
		// 뱅뱅 사거리 ~ 신논현역 사거리 실제 위/경도, 자동차 이동 시간
		now = LocalDateTime.now();
		start = CycleInfo.builder()
			.geo(new Geo(37.48962, 127.03178))
			.intervalAt(now.minusSeconds(140))
			.build();

		end = new GpsTime(new Gps(37.504494, 127.024569), now);
	}

	@DisplayName("거리 연산 테스트")
	@Test
	void calculateDistanceTest() {
		calculator = new DistanceCalculator();
		// when
		int result = calculator.calculate(start, end);

		// then
		assertEquals(1800, result, 200);
		System.out.println(result);
	}

	@DisplayName("방향 연산 테스트")
	@Test
	void calculateDirectionTest() {
		calculator = new DirectionCalculator();
		// when
		int result = calculator.calculate(start, end);

		// then
		assertEquals(315, result, 45);
		System.out.println(result);
	}

	@DisplayName("속도 연산 테스트")
	@Test
	void calculateSpeedTest() {
		calculator = new SpeedCalculator();
		// when
		int result = calculator.calculate(start, end);

		// then
		assertEquals(80, result, 40);
		System.out.println(result);
	}
}