package org.rabbitmqcollector.location.scheduler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.rabbitmqcollector.location.application.port.CycleInfoRepository;
import org.rabbitmqcollector.location.application.service.RedisService;
import org.rabbitmqcollector.location.application.service.VehicleUpdaterService;
import org.rabbitmqcollector.location.domain.CycleInfo;
import org.rabbitmqcollector.location.infrastructure.jpa.entity.VehicleInformationJpaRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class LocationSaveScheduler {
	private final RedisService redisService;
	private final CycleInfoRepository cycleInfoRepository;
	private final VehicleUpdaterService vehicleUpdaterService;

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

		vehicleUpdaterService.updateVehicleInformation(latestInfoMap); // ← 여기로 분리
		cycleInfoRepository.saveAll(batch);

		log.info("MySQL 적재 완료: {}건", batch.size());
	}
}
