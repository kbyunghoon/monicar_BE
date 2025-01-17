package org.emulator.device.presentation;

import lombok.RequiredArgsConstructor;

import org.common.dto.CommonResponse;
import org.emulator.device.application.VehicleService;
import org.emulator.device.common.response.BaseResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/emulator")
public class VehicleApiController {
	private final VehicleService vehicleService;

	@PostMapping("/key-on")
	public BaseResponse<Void> keyOn() {
		CommonResponse response = vehicleService.onVehicle();

		if (!response.rstCd().equals("000"))
			return BaseResponse.fail(response);

		return BaseResponse.success();
	}
}
