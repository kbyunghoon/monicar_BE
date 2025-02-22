package org.emulator;

import org.emulator.sensor.GpsSensor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

import java.util.TimeZone;

@Slf4j
@SpringBootApplication(scanBasePackages = {"org.emulator"})
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
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		log.info("[Thread: {}] {}", Thread.currentThread().getName(), "EmulatorApplication run()");
		SpringApplication.run(EmulatorApplication.class, args);
	}
}
