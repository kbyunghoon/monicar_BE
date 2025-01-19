package org.producer.presentation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.common.dto.CommonResponse;
import org.producer.application.VehicleEventService;
import org.producer.application.VehicleService;
import org.producer.common.response.BaseResponse;
import org.producer.common.response.EmulatorResponseCode;
import org.producer.domain.VehicleEvent;
import org.producer.domain.VehicleEventCreate;
import org.producer.domain.VehicleInformation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/producer")
public class OnOffController {
	private final VehicleService vehicleService;
	private final VehicleEventService vehicleEventService;

	@PostMapping("/key-on")
	public BaseResponse<CommonResponse> keyOn(
		@Valid @RequestBody final KeyOnRequest request
	) {
		/*
		 * TODO 토큰 확인 로직 추가
		 */
		VehicleInformation vehicleInformation = vehicleService.getVehicleInformation(request.mdn());

		VehicleEvent vehicleEvent = vehicleEventService.getRecentVehicleEvent(vehicleInformation.getId());
		if (vehicleEvent.isTypeOn()) {
			return BaseResponse.emulatorFail(EmulatorResponseCode.WRONG_APPROACH, request.mdn());
		}

		VehicleEventCreate vehicleEventCreate = request.toDomain(vehicleInformation.getId(), vehicleInformation.getSum());
		vehicleEventService.saveVehicleEvent(vehicleEventCreate);

		return BaseResponse.emulatorSuccess(request.mdn());
	}
}
