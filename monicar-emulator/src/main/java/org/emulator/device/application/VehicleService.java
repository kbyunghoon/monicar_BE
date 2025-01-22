package org.emulator.device.application;

import lombok.extern.slf4j.Slf4j;

import org.common.dto.CommonResponse;
import org.emulator.device.application.port.EmulatorRepository;
import org.emulator.device.application.port.LocationReceiver;
import org.emulator.device.application.port.VehicleEventSender;
import org.emulator.device.common.response.BaseResponse;
import org.emulator.device.domain.GpsStatus;
import org.emulator.device.domain.OnInfo;
import org.emulator.device.infrastructure.GpsTime;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Slf4j
public class VehicleService {
	private final EmulatorRepository emulatorRepository;
	private final LocationReceiver locationReceiver;
	private final VehicleEventSender vehicleEventSender;

	public BaseResponse<Void> onVehicle() {
		GpsTime onLocation = locationReceiver.fetchLocationRecent();

		OnInfo onInfo = OnInfo.create(
			onLocation.intervalAt(),
			GpsStatus.A,
			onLocation.location().lat(),
			onLocation.location().lng(),
			emulatorRepository.getCurrentDistance()
		);

		CommonResponse response = vehicleEventSender.sendOnEvent(onInfo);

		log.debug("response code: {}", response.rstCd());

		emulatorRepository.turnOn();
		emulatorRepository.startLogDistance();
		return BaseResponse.success();
	}
}
