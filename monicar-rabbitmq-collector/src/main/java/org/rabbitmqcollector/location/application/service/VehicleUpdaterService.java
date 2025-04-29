package org.rabbitmqcollector.location.application.service;

import java.util.Map;

import org.rabbitmqcollector.location.domain.CycleInfo;
import org.rabbitmqcollector.location.infrastructure.jpa.entity.VehicleInformationEntity;
import org.rabbitmqcollector.location.infrastructure.jpa.entity.VehicleInformationJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class VehicleUpdaterService {
	private final VehicleInformationJpaRepository vehicleInformationJpaRepository;

	@Transactional
	public void updateVehicleInformation(Map<Long, CycleInfo> latestInfoMap) {
		for (Map.Entry<Long, CycleInfo> entry : latestInfoMap.entrySet()) {
			Long vehicleId = entry.getKey();
			CycleInfo lastInfo = entry.getValue();

			VehicleInformationEntity vehicle = vehicleInformationJpaRepository.findById(vehicleId)
				.orElseThrow(() -> new EntityNotFoundException("차량 없음 vehicleId=" + vehicleId));

			vehicle.updateStatusAndLocationIfNeeded(lastInfo.getLat(), lastInfo.getLng());
		}
	}
}