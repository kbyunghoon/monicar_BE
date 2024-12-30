package org.emulator;

import org.emulator.sensor.GpsSensor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication(scanBasePackages = {"org.emulator", "org.common"})
public class EmulatorApplication {
	private final GpsSensor gpsSensor;

	public EmulatorApplication(GpsSensor gpsSensor) {
		this.gpsSensor = gpsSensor;
	}

	@PostConstruct
	public void initSensor() {
		gpsSensor.activate();
	}

	public static void main(String[] args) {
		log.info("[Thread: {}] {}", Thread.currentThread().getName(), "EmulatorApplication run()");
		SpringApplication.run(EmulatorApplication.class, args);
	}
}
