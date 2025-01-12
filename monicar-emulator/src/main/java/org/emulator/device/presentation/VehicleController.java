package org.emulator.device.presentation;

import org.common.dto.CommonResponse;
import org.emulator.device.application.VehicleService;
import org.emulator.device.common.response.BaseResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/emulator")
public class VehicleController {
	private final VehicleService vehicleService;

	@PostMapping("/key-on")
	public BaseResponse<Void> keyOn() {
		CommonResponse response = vehicleService.onVehicle();

		if (!response.rstCd().equals("000"))
			return BaseResponse.fail(response);

		return BaseResponse.success();
	}
}
