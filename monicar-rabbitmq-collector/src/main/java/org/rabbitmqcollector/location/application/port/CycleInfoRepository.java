package org.rabbitmqcollector.location.application.port;

import java.util.List;

import org.rabbitmqcollector.location.domain.CycleInfo;

public interface CycleInfoRepository {
	CycleInfo save(CycleInfo cycleInfo);

	CycleInfo findTopByVehicleIdOrderByCreatedAtDesc(Long id);

	void saveAll(List<CycleInfo> cycleInfos);
}
