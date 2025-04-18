package org.rabbitmqcollector.location.application.port;

import org.rabbitmqcollector.location.domain.CycleInfo;

public interface CycleInfoRepository {
	CycleInfo save(CycleInfo cycleInfo);

	CycleInfo findTopByVehicleIdOrderByCreatedAtDesc(Long id);
}
