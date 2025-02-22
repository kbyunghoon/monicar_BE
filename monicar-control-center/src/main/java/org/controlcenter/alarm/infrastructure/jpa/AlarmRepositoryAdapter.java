package org.controlcenter.alarm.infrastructure.jpa;

import org.controlcenter.alarm.application.port.AlarmRepository;
import org.controlcenter.alarm.domain.Alarm;
import org.controlcenter.alarm.infrastructure.jpa.entity.AlarmEntity;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AlarmRepositoryAdapter implements AlarmRepository {
	private final AlarmJpaRepository alarmJpaRepository;

	@Override
	public Alarm save(Alarm alarm) {
		return alarmJpaRepository.save(AlarmEntity.from(alarm))
			.toDomain();
	}
}
