package org.eventhub.infrastructure.repository;

import java.util.Optional;

import lombok.RequiredArgsConstructor;

import org.eventhub.application.port.AlarmRepository;
import org.eventhub.domain.Alarm;
import org.eventhub.domain.AlarmStatus;
import org.eventhub.infrastructure.repository.jpa.AlarmJpaRepository;
import org.eventhub.infrastructure.repository.jpa.entity.AlarmEntity;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class AlarmRepositoryAdapter implements AlarmRepository {
	private final AlarmJpaRepository alarmJpaRepository;

	@Override
	public Optional<Alarm>  findByVehicleId(Long vehicleId) {
		return alarmJpaRepository.findByVehicleId(vehicleId);
	}

	@Override
	public Long save(Long vehicleId) {
		Alarm newAlarm = Alarm.builder()
			.vehicleId(vehicleId)
			.isChecked(false)
			.status(AlarmStatus.REQUIRED)
			.build();

		return alarmJpaRepository.save(AlarmEntity.from(newAlarm))
			.toDomain().getId();
	}
}
