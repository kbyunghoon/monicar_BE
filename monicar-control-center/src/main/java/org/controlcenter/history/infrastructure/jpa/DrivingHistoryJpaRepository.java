package org.controlcenter.history.infrastructure.jpa;

import java.time.LocalDateTime;

import org.controlcenter.history.infrastructure.jpa.entity.DrivingHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DrivingHistoryJpaRepository extends JpaRepository<DrivingHistoryEntity, Long> {
	@Modifying
	@Query("UPDATE driving_history v SET v.deletedAt = :deletedAt WHERE v.vehicleId = :id")
	void softDeleteById(@Param("id") Long id, @Param("deletedAt") LocalDateTime deletedAt);
}
