package org.controlcenter.vehicle.application;

import org.controlcenter.vehicle.domain.VehicleEvent;
import org.controlcenter.vehicle.domain.VehicleEventType;
import org.controlcenter.vehicle.infrastructure.jpa.VehicleEventJpaRepository;
import org.controlcenter.vehicle.infrastructure.jpa.entity.VehicleEventEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("VehicleEventService 테스트")
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class VehicleEventServiceTest {
	@Autowired
	private VehicleEventService vehicleEventService;
	@Autowired
	private VehicleEventJpaRepository eventJpaRepository;

	@DisplayName("최근 이벤트가 On인지 확인")
	@Test
	void checkRecentVehicleEventIsOn_returnsTrue() {
		long vehicleId = 5L;

		String dateTimeStr = "2020011512";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHH");
		LocalDateTime eventAt = LocalDateTime.parse(dateTimeStr, formatter);

		eventJpaRepository.save(VehicleEventEntity.from(VehicleEvent.builder()
			.vehicleId(vehicleId)
			.type(VehicleEventType.ON)
			.eventAt(eventAt)
			.build()
		));

		VehicleEvent event = vehicleEventService.getRecentVehicleEvent(vehicleId);
		System.out.println(event.getType());
		assertTrue(event.isTypeOn());
	}
}