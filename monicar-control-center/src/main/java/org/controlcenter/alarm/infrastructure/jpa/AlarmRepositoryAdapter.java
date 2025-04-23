package org.controlcenter.alarm.infrastructure.jpa;

import java.util.Optional;

import org.controlcenter.alarm.application.port.AlarmRepository;
import org.controlcenter.alarm.domain.Alarm;
import org.controlcenter.alarm.infrastructure.jpa.entity.AlarmEntity;
import org.controlcenter.notice.domain.Notice;
import org.controlcenter.notice.infrastructure.jpa.entity.NoticeEntity;
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

	@Override
	public Optional<Alarm> findById(long alarmId) {
		return alarmJpaRepository.findById(alarmId).map(AlarmEntity::toDomain);
	}
}
