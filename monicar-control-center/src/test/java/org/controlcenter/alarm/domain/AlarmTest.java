package org.controlcenter.alarm.domain;

import static org.assertj.core.api.Assertions.*;

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
}