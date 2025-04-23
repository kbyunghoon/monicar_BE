package org.controlcenter.alarm.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.controlcenter.common.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("[domain 유닛테스트] Alarm 도메인")
class AlarmTest {
	@Test
	@DisplayName("Alarm 객체 생성 테스트")
	void createAlarm_success() {
		// Given
		AlarmCreate command = AlarmCreate.builder()
			.managerId("manager1")
			.vehicleId(1L)
			.drivingDistance(10000)
			.status(AlarmStatus.REQUIRED)
			.build();

		// When
		Alarm alarm = Alarm.create(command);

		// Then
		assertThat(alarm.getManagerId()).isEqualTo("manager1");
		assertThat(alarm.getVehicleId()).isEqualTo(1L);
		assertThat(alarm.getDrivingDistance()).isEqualTo(10000);
		assertThat(alarm.getStatus()).isEqualTo(AlarmStatus.REQUIRED);
	}

	@Test
	@DisplayName("AlarmStatus가 순차적 변경 테스트")
	void alarmStatus_transition_success() {
		// Given & When & Then
		assertThat(AlarmStatus.REQUIRED.next()).isEqualTo(AlarmStatus.SCHEDULED);
		assertThat(AlarmStatus.SCHEDULED.next()).isEqualTo(AlarmStatus.INPROGRESS);
		assertThat(AlarmStatus.INPROGRESS.next()).isEqualTo(AlarmStatus.COMPLETED);
	}

	@Test
	@DisplayName("AlarmStatus가 COMPLETED인 경우 next 호출 시 예외 발생 테스트")
	void alarmStatus_completed_throwsException() {
		// Expect
		assertThrows(BusinessException.class, AlarmStatus.COMPLETED::next);
	}
}