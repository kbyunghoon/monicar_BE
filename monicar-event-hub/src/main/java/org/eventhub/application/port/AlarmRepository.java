package org.eventhub.application.port;

import java.util.Optional;

import org.eventhub.domain.Alarm;

public interface AlarmRepository {
	Optional<Alarm> findByVehicleId(Long vehicleId);

	Optional<Alarm> findRecentOneByVehicleId(Long vehicleId);
	Long save(Long vehicleId);
}