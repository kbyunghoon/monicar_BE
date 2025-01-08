package org.controlcenter.vehicle.application;

import org.controlcenter.common.exception.BusinessException;
import org.controlcenter.common.response.code.ErrorCode;
import org.controlcenter.vehicle.application.port.VehicleRepository;
import org.controlcenter.vehicle.domain.VehicleInformation;
import org.controlcenter.vehicle.infrastructure.VehicleQueryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class VehicleService {
	private final VehicleQueryRepository vehicleQueryRepository;
	private final VehicleRepository vehicleRepository;

	public String getVehicleNumber(Long vehicleId) {
		VehicleInformation vehicleInformation = vehicleRepository.findById(vehicleId)
			.orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));
		return vehicleInformation.getVehicleNumber();
	}
}
