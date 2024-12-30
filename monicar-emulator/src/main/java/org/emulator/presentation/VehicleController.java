package org.emulator.presentation;

import lombok.RequiredArgsConstructor;

import org.emulator.application.VehicleService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("/api/v1/emulator")
public class VehicleController {
	private final VehicleService vehicleService;

	@PostMapping("/key-on")
	public void keyOn() {
		vehicleService.onVehicle();
	}
}
