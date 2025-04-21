package org.rabbitmqcollector.location.scheduler;

import java.time.LocalDateTime;
import java.util.List;

import org.rabbitmqcollector.location.application.port.CycleInfoRepository;
import org.rabbitmqcollector.location.application.port.VehicleEventRepository;
import org.rabbitmqcollector.location.application.port.VehicleInformationRepository;
import org.rabbitmqcollector.location.domain.CycleInfo;
import org.rabbitmqcollector.location.domain.VehicleEvent;
import org.rabbitmqcollector.location.domain.VehicleEventType;
import org.rabbitmqcollector.location.domain.VehicleInformation;
import org.rabbitmqcollector.location.domain.VehicleStatus;
import org.rabbitmqcollector.location.infrastructure.jpa.entity.VehicleInformationEntity;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class VehicleStatusCheckScheduler {

	private final RedisTemplate<String, String> redisTemplate;
	private final SimpMessagingTemplate messagingTemplate;
	private final VehicleInformationRepository vehicleInformationRepository;
	private final CycleInfoRepository cycleInfoRepository;
	private final VehicleEventRepository vehicleEventRepository;

	@Scheduled(fixedRate = 30000)
	@Transactional
	public void updateInactiveVehicles() {
		LocalDateTime threshold = LocalDateTime.now().minusSeconds(120);

		List<VehicleInformationEntity> vehicles = vehicleInformationRepository.findByStatus(VehicleStatus.IN_OPERATION);
		for (VehicleInformationEntity vehicleEntity : vehicles) {
			VehicleInformation vehicle = vehicleEntity.toDomain();

			CycleInfo latestCycleInfo = cycleInfoRepository
				.findTopByVehicleIdOrderByCreatedAtDesc(vehicle.getId());

			if (latestCycleInfo == null || latestCycleInfo.getIntervalAt().isBefore(threshold)) {
				vehicleEntity.updateStatus(VehicleStatus.NOT_DRIVEN);

				var vehicleEvent = VehicleEvent.builder()
					.vehicleId(vehicle.getId())
					.type(VehicleEventType.OFF)
					.eventAt(LocalDateTime.now())
					.build();

				vehicleEventRepository.save(vehicleEvent);

				log.info("[상태변경] 차량 {} → 미운행 상태로 변경됨", vehicle.getId());
			}
		}
	}
}
