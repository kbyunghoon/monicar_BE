package org.emulator.device.application;

import org.emulator.device.application.port.EmulatorRepository;
import org.emulator.device.application.port.LocationReceiver;
import org.emulator.device.application.port.VehicleEventSender;
import org.emulator.device.common.response.BaseResponse;
import org.emulator.device.domain.GpsStatus;
import org.emulator.device.domain.OnInfo;
import org.emulator.device.infrastructure.GpsTime;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class VehicleService {
	private final EmulatorRepository emulatorRepository;
	private final LocationReceiver locationReceiver;
	private final VehicleEventSender vehicleEventSender;

	public BaseResponse onVehicle() {
		GpsTime onLocation = locationReceiver.getLocation();

		OnInfo onInfo = OnInfo.create(
			onLocation.intervalAt(),
			GpsStatus.A,
			onLocation.location().lat(),
			onLocation.location().lng(),
			emulatorRepository.getCurrentDistance()
		);
		log.info("key-on request in emulator ! ");

		vehicleEventSender.sendOnEvent(onInfo);

		emulatorRepository.turnOn();
		emulatorRepository.startLogDistance();
		return BaseResponse.success();
	}
}
