package org.eventhub.presentation;

import java.util.Optional;

import org.eventhub.application.VehicleEventService;
import org.eventhub.application.VehicleService;
import org.eventhub.common.response.BaseResponse;
import org.eventhub.common.response.ErrorCode;
import org.eventhub.domain.VehicleEvent;
import org.eventhub.domain.VehicleEventCreate;
import org.eventhub.domain.VehicleInformation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/event-hub")
@Slf4j
public class OnOffController {
	private final VehicleService vehicleService;
	private final VehicleEventService vehicleEventService;

	@PostMapping("/key-on")
	public BaseResponse keyOn(
		@Valid @RequestBody final KeyOnRequest request
	) {
		log.info("emulator on request in event-hub ! ");
		VehicleInformation vehicleInformation = vehicleService.getVehicleInformation(request.mdn());

		Optional<VehicleEvent> vehicleEvent = vehicleEventService.getRecentVehicleEvent(vehicleInformation.getId());
		boolean isAlreadyOn = vehicleEvent.map(VehicleEvent::isTypeOn).orElse(false);

		log.info("is already on? : {}", isAlreadyOn);
		if (isAlreadyOn) {
			return BaseResponse.fail(ErrorCode.WRONG_APPROACH);
		}
		VehicleEventCreate vehicleEventCreate = request.toDomain(vehicleInformation.getId(), vehicleInformation.getSum());
		vehicleEventService.saveVehicleEvent(vehicleEventCreate);

		return BaseResponse.success();
	}
}
