package org.emulator.device.application;

import org.emulator.device.application.port.EmulatorRepository;
import org.emulator.device.application.port.LocationReceiver;
import org.emulator.device.application.port.VehicleEventSender;
import org.emulator.device.common.response.BaseResponse;
import org.emulator.device.domain.GpsStatus;
import org.emulator.device.domain.OffInfo;
import org.emulator.device.domain.OnInfo;
import org.emulator.sensor.dto.GpsTime;
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
		GpsTime onLocation = locationReceiver.fetchLocationRecent();

		OnInfo onInfo = OnInfo.create(
			onLocation.intervalAt(),
			GpsStatus.A,
			onLocation.location().lat(),
			onLocation.location().lng(),
			emulatorRepository.getTotalDistance()
		);

		vehicleEventSender.sendOnEvent(onInfo);

		emulatorRepository.turnOn();
		emulatorRepository.startLogDistance();
		return BaseResponse.success();
	}

    public BaseResponse offVehicle() {
		log.info("key-off request in emulator ! ");
		GpsTime offLocation = locationReceiver.fetchLocationRecent();

		emulatorRepository.turnOff();
		emulatorRepository.updateTotalDistance();
		log.info("off lat: {}, lng: {}", offLocation.location().lat(), offLocation.location().lng());
		log.info("off time: {}", offLocation.intervalAt());

		OffInfo offInfo = OffInfo.create(
			offLocation.intervalAt(),
			GpsStatus.A,
			offLocation.location().lat(),
			offLocation.location().lng(),
			emulatorRepository.getCurrentDistance()
		);

		vehicleEventSender.sendOffEvent(offInfo);
		return BaseResponse.success();
    }
}
