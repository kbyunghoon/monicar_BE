package org.emulator.sensor;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class GpsDetector implements SensorDetector {

	@Scheduled(initialDelay = 1000, fixedDelay = 1000)
	@Override
	public void detect() {
		log.info("[Thread: {}] {}", Thread.currentThread().getName(), "Detecting... GPS");
	}
}
