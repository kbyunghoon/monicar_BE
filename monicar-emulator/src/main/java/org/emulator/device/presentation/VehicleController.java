package org.emulator.device.presentation;

import org.emulator.device.application.VehicleService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController("/api/v1/emulator")
public class VehicleController {
	private final VehicleService vehicleService;

	@PostMapping("/key-on")
	public void keyOn() {
		vehicleService.onVehicle();
	}
}
