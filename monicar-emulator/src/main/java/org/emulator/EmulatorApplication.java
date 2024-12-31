package org.emulator;

import org.emulator.device.application.VehicleService;
import org.emulator.sensor.GpsSensor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication(scanBasePackages = {"org.emulator", "org.common"})
public class EmulatorApplication {
	private final GpsSensor gpsSensor;
	private final VehicleService vehicleService;

	public EmulatorApplication(GpsSensor gpsSensor, VehicleService vehicleService) {
		this.gpsSensor = gpsSensor;
		this.vehicleService = vehicleService;
	}

	@PostConstruct
	public void initSensor() {
		gpsSensor.activate();
		vehicleService.trackVehicle();
	}

	public static void main(String[] args) {
		log.info("[Thread: {}] {}", Thread.currentThread().getName(), "EmulatorApplication run()");
		SpringApplication.run(EmulatorApplication.class, args);
	}
}
