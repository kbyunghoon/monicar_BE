package org.producer.infrastructure.vehicle_event;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.producer.application.port.VehicleEventRepository;
import org.producer.config.JpaConfig;
import org.producer.domain.VehicleEvent;
import org.producer.domain.VehicleEventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

@DataJpaTest
@ActiveProfiles("test")
@Testcontainers
@Import({JpaConfig.class, VehicleEventRepositoryAdapter.class})
class VehicleEventJpaRepositoryTest {

	@Autowired
	private VehicleEventRepository repository;

	@Autowired
	private VehicleEventJpaRepository jpaRepository;

	@Test
	void findLatestById() {
		// given
		VehicleEventEntity vehicleEventEntity1 = VehicleEventEntity.from(
			VehicleEvent.builder()
				.vehicleId(1L)
				.type(VehicleEventType.ON)
				.eventAt(LocalDateTime.now())
				.build()
		);
		jpaRepository.save(vehicleEventEntity1);

		VehicleEventEntity vehicleEventEntity2 = VehicleEventEntity.from(
			VehicleEvent.builder()
				.vehicleId(1L)
				.type(VehicleEventType.OFF)
				.eventAt(LocalDateTime.now())
				.build()
		);
		jpaRepository.save(vehicleEventEntity2);

		// when
		VehicleEvent latestEvent = repository.findLatestById(1L).get();

		// then
		assertThat(latestEvent.getType()).isEqualTo(VehicleEventType.OFF);
	}
}