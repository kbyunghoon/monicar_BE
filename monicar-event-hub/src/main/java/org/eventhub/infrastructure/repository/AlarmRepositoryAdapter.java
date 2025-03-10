package org.eventhub.infrastructure.repository;

import java.util.Optional;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

import org.eventhub.application.port.AlarmRepository;
import org.eventhub.domain.Alarm;
import org.eventhub.domain.AlarmStatus;
import org.eventhub.infrastructure.repository.jpa.AlarmJpaRepository;
import org.eventhub.infrastructure.repository.jpa.entity.AlarmEntity;
import org.eventhub.infrastructure.repository.jpa.entity.QAlarmEntity;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class AlarmRepositoryAdapter implements AlarmRepository {
	private final AlarmJpaRepository alarmJpaRepository;
	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public Optional<Alarm> findByVehicleId(Long vehicleId) {
		return alarmJpaRepository.findByVehicleId(vehicleId);
	}

	@Override
	public Optional<Alarm> findRecentOneByVehicleId(Long vehicleId) {
		QAlarmEntity alarmEntity = QAlarmEntity.alarmEntity;

		Optional<AlarmEntity> alarm = Optional.ofNullable(jpaQueryFactory.select(alarmEntity)
			.from(alarmEntity)
			.where(alarmEntity.vehicleId.eq(vehicleId))
			.orderBy(alarmEntity.createdAt.desc())
			.fetchFirst()
		);
		return alarm.map(AlarmEntity::toDomain);
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
