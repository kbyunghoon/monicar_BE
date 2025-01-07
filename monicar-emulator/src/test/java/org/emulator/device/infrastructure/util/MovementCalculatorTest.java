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
class MovementCalculatorTest {
	static final double START_LATITUDE = 37.48962;
	static final double START_LONGITUDE = 127.03178;
	static final double END_LATITUDE = 37.504494;
	static final double END_LONGITUDE = 127.024569;
	static final long MOVEMENT_TIME_SEC = 140;

	LocalDateTime now = LocalDateTime.now();
	LocalDateTime twoMinutesBefore = now.minusSeconds(MOVEMENT_TIME_SEC);

	CycleInfo start;
	GpsTime end;

	MovementCalculator movementCalculator;

	@BeforeEach
	void setUp() {
		start = CycleInfo.builder()
			.geo(new Geo(START_LATITUDE, START_LONGITUDE))
			.intervalAt(twoMinutesBefore)
			.build();

		end = new GpsTime(new Gps(END_LATITUDE, END_LONGITUDE), now);
	}

	@DisplayName("거리 연산 테스트")
	@Test
	void calculateDistanceTest() {
		int expectedMeter = 1800;
		int plusMinusMeter = 200;

		// given
		movementCalculator = new DistanceCalculator();

		// when
		int resultMeter = movementCalculator.calculate(start, end);

		// then
		assertEquals(expectedMeter, resultMeter, plusMinusMeter);
		System.out.println(resultMeter);
	}

	@DisplayName("방향 연산 테스트")
	@Test
	void calculateDirectionTest() {
		int expectedAngle = 315;
		int plusMinusAngle = 45;

		// given
		movementCalculator = new DirectionCalculator();

		// when
		int resultAngle = movementCalculator.calculate(start, end);

		// then
		assertEquals(expectedAngle, resultAngle, plusMinusAngle);
		System.out.println(resultAngle);
	}

	@DisplayName("속도 연산 테스트")
	@Test
	void calculateSpeedTest() {
		int expectedKiloMeterPerHour = 80;
		int plusMinusKiloMeterPerHour = 40;

		// given
		movementCalculator = new SpeedCalculator();

		// when
		int resultKiloMeterPerHour = movementCalculator.calculate(start, end);

		// then
		assertEquals(expectedKiloMeterPerHour, resultKiloMeterPerHour, plusMinusKiloMeterPerHour);
		System.out.println(resultKiloMeterPerHour);
	}
}