package org.emulator.sensor;

import java.util.concurrent.ConcurrentLinkedQueue;

import java.util.concurrent.LinkedBlockingDeque;

import org.emulator.sensor.dto.Gps;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Component
@Slf4j
public class GpsDetector implements SensorDetector {
	private final LocationHolder locationHolder;

	@Scheduled(initialDelay = 1000, fixedDelay = 1000)
	@Override
	public void detect() {
		log.info("[Thread: {}] {}", Thread.currentThread().getName(), "Detecting... GPS");
		locationHolder.addGpsData(new Gps(20.111111, 30.111111));
	}
}
