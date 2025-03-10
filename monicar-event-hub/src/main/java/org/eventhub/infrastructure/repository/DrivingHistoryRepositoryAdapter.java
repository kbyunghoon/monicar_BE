package org.eventhub.infrastructure.repository;

import lombok.RequiredArgsConstructor;

import org.eventhub.application.port.DrivingHistoryRepository;
import org.eventhub.domain.DrivingHistory;
import org.eventhub.infrastructure.repository.jpa.entity.DrivingHistoryEntity;
import org.eventhub.infrastructure.repository.jpa.DrivingHistoryJpaRepository;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class DrivingHistoryRepositoryAdapter implements DrivingHistoryRepository {
	private final DrivingHistoryJpaRepository drivingHistoryJpaRepository;

	@Override
	public DrivingHistory save(DrivingHistory drivingHistory) {

		return drivingHistoryJpaRepository.save(DrivingHistoryEntity.from(drivingHistory))
			.toDomain();
	}
}
