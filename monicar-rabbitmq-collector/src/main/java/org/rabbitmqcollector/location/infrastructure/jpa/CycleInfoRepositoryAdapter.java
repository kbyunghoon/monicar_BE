package org.rabbitmqcollector.location.infrastructure.jpa;

import org.rabbitmqcollector.location.application.port.CycleInfoRepository;
import org.rabbitmqcollector.location.domain.CycleInfo;
import org.rabbitmqcollector.location.infrastructure.jpa.entity.CycleInfoEntity;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CycleInfoRepositoryAdapter implements CycleInfoRepository {
	private final CycleInfoJpaRepository cycleInfoJpaRepository;

	@Override
	public CycleInfo save(CycleInfo cycleInfo) {
		return cycleInfoJpaRepository.save(CycleInfoEntity.from(cycleInfo)).toDomain();
	}

	@Override
	public CycleInfo findTopByVehicleIdOrderByCreatedAtDesc(Long id) {
		return cycleInfoJpaRepository.findTopByVehicleIdOrderByCreatedAtDesc(id)
			.map(CycleInfoEntity::toDomain)
			.orElse(null);

	}
}
