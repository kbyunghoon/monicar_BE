package org.controlcenter.alarm.application.port;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.time.LocalDateTime;

import org.controlcenter.alarm.domain.Alarm;
import org.controlcenter.alarm.domain.AlarmStatus;
import org.controlcenter.alarm.infrastructure.jpa.AlarmRepositoryAdapter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@Import({AlarmRepositoryAdapter.class})
class AlarmRepositoryTest {
	@Autowired
	private AlarmRepository alarmRepository;

	@Test
	@DisplayName("Alarm을 저장하여 ID로 조회 테스트")
	void save_and_findById_success() {
		// Given
		Alarm alarm = Alarm.builder()
			.managerId("manager123")
			.vehicleId(1L)
			.drivingDistance(10000)
			.status(AlarmStatus.REQUIRED)
			.isChecked(false)
			.createdAt(LocalDateTime.now())
			.build();

		// When
		Alarm saved = alarmRepository.save(alarm);
		Alarm found = alarmRepository.findById(saved.getId()).orElseThrow();

		// Then
		assertThat(found.getVehicleId()).isEqualTo(1L);
		assertThat(found.getStatus()).isEqualTo(AlarmStatus.REQUIRED);
	}
}
