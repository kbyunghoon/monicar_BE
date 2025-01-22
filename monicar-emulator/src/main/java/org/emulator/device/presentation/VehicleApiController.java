package org.emulator.device.presentation;

import org.emulator.device.application.VehicleService;
import org.emulator.device.common.response.BaseResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/emulator")
public class VehicleApiController {
	private final VehicleService vehicleService;

	@PostMapping("/key-on")
	public BaseResponse keyOn() {
		return vehicleService.onVehicle();
	}
}
