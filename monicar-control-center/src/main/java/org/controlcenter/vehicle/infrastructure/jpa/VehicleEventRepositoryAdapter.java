package org.controlcenter.vehicle.infrastructure.jpa;

import static org.controlcenter.vehicle.infrastructure.jpa.entity.QVehicleEventEntity.*;

import com.querydsl.core.Tuple;

import java.util.Objects;
import java.util.Optional;

import org.controlcenter.vehicle.application.port.VehicleEventRepository;
import org.controlcenter.vehicle.domain.VehicleEvent;
import org.controlcenter.vehicle.infrastructure.jpa.entity.VehicleEventEntity;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class VehicleEventRepositoryAdapter implements VehicleEventRepository {
	private final VehicleEventJpaRepository vehicleEventJpaRepository;
	private final JPAQueryFactory queryFactory;

	@Override
	public VehicleEvent save(VehicleEvent vehicleEvent) {
		return vehicleEventJpaRepository.save(VehicleEventEntity.from(vehicleEvent)).toDomain();
	}

	@Override
	public Optional<VehicleEvent> findLatestById(long vehicleId) {
		Tuple result = queryFactory.select(
				vehicleEventEntity.id,
				vehicleEventEntity.type
			)
			.from(vehicleEventEntity)
			.where(vehicleEventEntity.vehicleId.eq(vehicleId))
			.orderBy(vehicleEventEntity.updatedAt.desc())
			.fetchFirst();

		return Optional.ofNullable(VehicleEvent.builder()
			.id(result.get(vehicleEventEntity.id))
			.eventAt(result.get(vehicleEventEntity.eventAt))
			.build()
		);
	}
}
