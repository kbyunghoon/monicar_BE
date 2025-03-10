package org.controlcenter.vehicle.infrastructure.jpa;

import com.querydsl.core.Tuple;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

import org.controlcenter.vehicle.application.port.VehicleEventRepository;
import org.controlcenter.vehicle.domain.VehicleEvent;
import org.controlcenter.vehicle.infrastructure.jpa.entity.VehicleEventEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static org.controlcenter.vehicle.infrastructure.jpa.entity.QVehicleEventEntity.*;

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
		Optional<Tuple> result = Optional.ofNullable(
			queryFactory.select(
					vehicleEventEntity.id,
					vehicleEventEntity.type
				)
				.from(vehicleEventEntity)
				.where(vehicleEventEntity.vehicleId.eq(vehicleId))
				.orderBy(vehicleEventEntity.createdAt.desc())
				.fetchFirst()
		);

		return result.map(tuple -> VehicleEvent.builder()
			.id(tuple.get(vehicleEventEntity.id))
			.type(tuple.get(vehicleEventEntity.type))
			.build()
		);
	}
}
