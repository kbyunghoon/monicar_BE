package org.eventhub.infrastructure.repository.jpa;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.eventhub.application.port.VehicleEventRepository;
import org.eventhub.config.JpaConfig;
import org.eventhub.domain.VehicleEvent;
import org.eventhub.domain.VehicleEventType;
import org.eventhub.infrastructure.repository.VehicleEventRepositoryAdapter;
import org.eventhub.infrastructure.repository.jpa.entity.VehicleEventEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

@DisplayName("vehicle_event jpa 테스트")
@DataJpaTest
@ActiveProfiles("test")
@Testcontainers
@Import({JpaConfig.class, VehicleEventRepositoryAdapter.class})
class VehicleEventJpaRepositoryTest {

	@Autowired
	private VehicleEventRepository repository;

	@Autowired
	private VehicleEventJpaRepository jpaRepository;

	@DisplayName("vehicle_event JPQL findLatestByVehicleId 테스트")
	@Test
	void findLatestByVehicleIdSuccess() {
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
		VehicleEvent latestEvent = repository.findLatestByVehicleId(1L).get();

		// then
		assertThat(latestEvent.getType()).isEqualTo(VehicleEventType.OFF);
	}
}