package org.controlcenter.vehicle.infrastructure.jpa;

import java.util.Optional;

import org.controlcenter.vehicle.domain.VehicleEvent;
import org.controlcenter.vehicle.infrastructure.jpa.entity.VehicleEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleEventJpaRepository extends JpaRepository<VehicleEventEntity, Long> {
}
