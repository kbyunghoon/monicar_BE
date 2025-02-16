package org.eventhub.infrastructure.repository.jpa;

import java.util.Optional;

import org.eventhub.domain.Alarm;
import org.eventhub.infrastructure.repository.jpa.entity.AlarmEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmJpaRepository extends JpaRepository<AlarmEntity, Long> {
	Optional<Alarm> findByVehicleId(Long vehicleId);
}
