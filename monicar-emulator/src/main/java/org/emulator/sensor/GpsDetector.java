package org.emulator.sensor;

import org.emulator.device.application.port.EmulatorRepository;
import org.emulator.sensor.dto.GpsListCircular;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Component
@Slf4j
public class GpsDetector implements SensorDetector {
	private final LocationHolder locationHolder;
	private final EmulatorRepository emulatorRepository;
	private final GpxFileParser gpxFileParser;
	private final GpsListCircular gpsListCircular;

	@PostConstruct
	public void loadFile() {
		gpsListCircular.loadFileData(gpxFileParser.parse());
	}

	@Scheduled(initialDelay = 1000, fixedDelay = 1000)
	@Override
	public void detect() {
		log.info("[Thread: {}] {}", Thread.currentThread().getName(), "Detecting... GPS");
		if (emulatorRepository.isTurnOn()) {
			locationHolder.addGpsData(gpsListCircular.getMovingGps());
		} else {
			locationHolder.addGpsData(gpsListCircular.getStopGps());
		}
	}
}
