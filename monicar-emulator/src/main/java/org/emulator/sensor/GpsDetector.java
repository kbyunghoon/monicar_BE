package org.emulator.sensor;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.emulator.pipe.Gps;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class GpsDetector implements SensorDetector {
	private final ConcurrentLinkedQueue<Gps> gpsPipeQueue;

	@Scheduled(initialDelay = 1000, fixedDelay = 1000)
	@Override
	public void detect() {
		log.info("[Thread: {}] {}", Thread.currentThread().getName(), "Detecting... GPS");
		gpsPipeQueue.add(new Gps(20.111111, 30.111111));
	}
}
