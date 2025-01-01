package org.emulator.sensor;

import org.emulator.device.infrastructure.SensorTracker;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Component
public class GpsSensor {
	private final SensorDetector sensorDetector;
	private final SensorTracker sensorTracker;

	@Async
	public void activate() {
		log.info("[Thread: {}] {}", Thread.currentThread().getName(), "Activating GPS sensor");

		// 스케쥴링 작업을 통한 GPS 데이터 수집
		sensorDetector.detect();
		// 스케쥴링 작업을 통한 GPS 데이터 변환 및 전송
		sensorTracker.track();
	}
}
