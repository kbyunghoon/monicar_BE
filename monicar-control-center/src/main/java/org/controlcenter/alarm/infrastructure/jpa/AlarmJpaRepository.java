package org.controlcenter.alarm.infrastructure.jpa;

import java.util.List;
import java.util.Optional;

import org.controlcenter.alarm.domain.AlarmInfo;
import org.controlcenter.alarm.domain.AlarmStatus;
import org.controlcenter.alarm.domain.AlarmStatusStats;
import org.controlcenter.alarm.infrastructure.jpa.entity.AlarmEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AlarmJpaRepository extends JpaRepository<AlarmEntity, Long> {
	Optional<AlarmEntity> findByIdAndIsCheckedFalse(Long id);

	@Query("SELECT new org.controlcenter.alarm.domain.AlarmInfo(a.id, vi.vehicleNumber, m.nickname, a.status) " +
		"FROM alarm a " +
		"JOIN vehicle_information vi ON a.vehicleId = vi.id " +
		"LEFT JOIN manager m ON a.managerId = m.id " +
		"WHERE (:status IS NULL OR a.status = :status) " +
		"  AND (:status IS NULL OR :status <> 'REQUIRED' OR a.managerId IS NULL) " +
		"  AND a.isChecked = FALSE " +
		"ORDER BY a.createdAt DESC")
	Page<AlarmInfo> findAlarmListByStatus(@Param("status") AlarmStatus status,
		Pageable pageable);

	@Query("""
		    SELECT new org.controlcenter.alarm.domain.AlarmStatusStats(
		        SUM(CASE WHEN a.status = 'REQUIRED' THEN 1 ELSE 0 END),
		        SUM(CASE WHEN a.status = 'SCHEDULED' THEN 1 ELSE 0 END),
		        SUM(CASE WHEN a.status = 'IN_PROGRESS' THEN 1 ELSE 0 END),
		        SUM(CASE WHEN a.status = 'COMPLETED' THEN 1 ELSE 0 END)
		    )
		    FROM alarm a
		""")
	AlarmStatusStats findStatusCounts();
}
