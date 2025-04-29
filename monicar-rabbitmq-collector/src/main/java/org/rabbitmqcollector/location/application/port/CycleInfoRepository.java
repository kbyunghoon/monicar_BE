package org.rabbitmqcollector.location.application.port;

import java.util.List;

import org.rabbitmqcollector.location.domain.CycleInfo;
import org.rabbitmqcollector.location.infrastructure.jpa.entity.CycleInfoEntity;

public interface CycleInfoRepository {
	CycleInfo save(CycleInfo cycleInfo);

	CycleInfo findTopByVehicleIdOrderByCreatedAtDesc(Long id);

	void saveAll(List<CycleInfoEntity> cycleInfos);
}
