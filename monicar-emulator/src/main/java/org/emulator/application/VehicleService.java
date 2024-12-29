package org.emulator.application;

import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;

import org.common.dto.CommonResponse;
import org.emulator.application.port.EmulatorRepository;
import org.emulator.application.port.LocationEventPublisher;
import org.emulator.application.port.VehicleCommandSender;
import org.emulator.domain.OnInfo;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class VehicleService {
    private final EmulatorRepository emulatorRepository;
    private final LocationEventPublisher locationEventPublisher;
    private final VehicleCommandSender vehicleCommandSender;

	public void onVehicle() {
		OnInfo onInfo = OnInfo.builder()
			.onTime(LocalDateTime.now())
			.gpsStatus(emulatorRepository.getGpsStatus())
			.totalDistance(emulatorRepository.getTotalDistance())
			.build();

		CommonResponse response = vehicleCommandSender.sendOnCommand(onInfo);
	}
}
