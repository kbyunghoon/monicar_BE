package org.controlcenter.vehicle.application;

import java.util.Optional;

import org.controlcenter.common.exception.BusinessException;
import org.controlcenter.common.response.code.ErrorCode;
import org.controlcenter.vehicle.application.port.VehicleEventRepository;
import org.controlcenter.vehicle.domain.VehicleEvent;
import org.controlcenter.vehicle.domain.VehicleEventCreate;
import org.controlcenter.vehicle.domain.VehicleEventType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class VehicleEventService {
	private final VehicleEventRepository vehicleEventRepository;

	@Transactional
	public VehicleEvent saveVehicleEvent(final VehicleEventCreate command) {
		return vehicleEventRepository.save(VehicleEvent.create(command));
	}

	public boolean checkRecentVehicleEventIsOn(long vehicleId) {
		VehicleEvent vehicleEvent = vehicleEventRepository
			.findLatestById(vehicleId)
			.orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));

		return vehicleEvent.isTypeOn();
	}
}
