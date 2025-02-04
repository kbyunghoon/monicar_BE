package org.eventhub.infrastructure.repository.jpa;

import org.eventhub.infrastructure.repository.jpa.entity.DrivingHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrivingHistoryJpaRepository extends JpaRepository<DrivingHistoryEntity, Long> {
}
