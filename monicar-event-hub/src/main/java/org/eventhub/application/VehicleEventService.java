package org.eventhub.application;

import java.util.Optional;

import org.eventhub.application.port.VehicleEventRepository;
import org.eventhub.domain.VehicleEvent;
import org.eventhub.domain.VehicleEventCreate;
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

	@Transactional(readOnly = true)
	public Optional<VehicleEvent> getRecentVehicleEvent(long vehicleId) {
		return vehicleEventRepository.findLatestByVehicleId(vehicleId);
	}
}
