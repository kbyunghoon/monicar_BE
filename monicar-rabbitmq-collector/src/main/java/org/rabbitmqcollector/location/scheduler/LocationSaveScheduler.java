package org.rabbitmqcollector.location.scheduler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.rabbitmqcollector.location.application.port.CycleInfoRepository;
import org.rabbitmqcollector.location.application.service.RedisService;
import org.rabbitmqcollector.location.domain.CycleInfo;
import org.rabbitmqcollector.location.infrastructure.jpa.entity.VehicleInformationEntity;
import org.rabbitmqcollector.location.infrastructure.jpa.entity.VehicleInformationJpaRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class LocationSaveScheduler {
	private final RedisTemplate<String, String> redisTemplate;
	private final SimpMessagingTemplate messagingTemplate;
	private final CycleInfoRepository cycleInfoRepository;
	private final RedisService redisService;
	private final VehicleInformationJpaRepository vehicleInformationJpaRepository;

	@Scheduled(fixedRate = 60000)
	@Transactional
	public void persistToDatabase() {
		List<CycleInfo> batch = redisService.fetchAll();
		if (batch.isEmpty())
			return;

		Map<Long, CycleInfo> latestInfoMap = new HashMap<>();
		for (CycleInfo info : batch) {
			latestInfoMap.put(info.getVehicleId(), info);
		}

		Map<Long, VehicleInformationEntity> vehicleCache = new HashMap<>();
		for (Map.Entry<Long, CycleInfo> entry : latestInfoMap.entrySet()) {
			Long vehicleId = entry.getKey();
			CycleInfo lastInfo = entry.getValue();

			VehicleInformationEntity vehicle = vehicleCache.computeIfAbsent(vehicleId, id -> {
				try {
					return vehicleInformationJpaRepository
						.findById(id)
						.orElseThrow(() -> new EntityNotFoundException("차량 없음"));
				} catch (Exception e) {
					log.warn("🚫 차량 조회 실패 → vehicleId={}", id);
					return null;
				}
			});

			if (vehicle != null) {
				vehicle.updateStatusAndLocationIfNeeded(lastInfo.getLat(), lastInfo.getLng());
			}
		}

		try {
			cycleInfoRepository.saveAll(batch);
			log.info("MySQL 적재 완료: {}건", batch.size());
		} catch (ObjectOptimisticLockingFailureException e) {
			log.warn("낙관적 락 충돌 발생, 개별 저장 시도");
			// 개별 저장 시도
			for (CycleInfo info : batch) {
				try {
					cycleInfoRepository.save(info);
				} catch (Exception ex) {
					log.error("개별 저장 실패: vehicleId={}, error={}", info.getVehicleId(), ex.getMessage());
				}
			}
		}
	}
}