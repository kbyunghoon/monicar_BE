package org.emulator.device.application;

import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;

import org.common.dto.CommonResponse;
import org.emulator.device.application.port.EmulatorRepository;
import org.emulator.device.application.port.LocationEventPublisher;
import org.emulator.device.application.port.VehicleCommandSender;
import org.emulator.device.domain.GpsStatus;
import org.emulator.device.domain.OnInfo;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class VehicleService {
    private final EmulatorRepository emulatorRepository;
    private final LocationEventPublisher locationEventPublisher;
    private final VehicleCommandSender vehicleCommandSender;

	public void onVehicle() {
		// 큐에서 on 관련 데이터 가져오기

		// 가져와서 OnInfo 생성
		OnInfo onInfo = OnInfo.createOnInfo(
			LocalDateTime.now(),
			GpsStatus.A,
			4140338,
			217403,
			5000);


		CommonResponse response = vehicleCommandSender.sendOnCommand(onInfo);
	}
}
