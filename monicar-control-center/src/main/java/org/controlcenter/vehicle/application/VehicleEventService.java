package org.controlcenter.vehicle.application;

import lombok.extern.slf4j.Slf4j;

import org.controlcenter.common.exception.BusinessException;
import org.controlcenter.common.response.code.ErrorCode;
import org.controlcenter.vehicle.application.port.VehicleEventRepository;
import org.controlcenter.vehicle.domain.VehicleEvent;
import org.controlcenter.vehicle.domain.VehicleEventCreate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Slf4j
@RequiredArgsConstructor
@Service
public class VehicleEventService {
	private final VehicleEventRepository vehicleEventRepository;

	@Transactional
	public VehicleEvent saveVehicleEvent(final VehicleEventCreate command) {
		return vehicleEventRepository.save(VehicleEvent.create(command));
	}

	public VehicleEvent getRecentVehicleEvent(long vehicleId) {
		return vehicleEventRepository
			.findLatestById(vehicleId)
			.orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));
	}
}
