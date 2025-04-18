package org.rabbitmqcollector.location.infrastructure.jpa;

import org.rabbitmqcollector.location.infrastructure.jpa.entity.VehicleEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleEventJpaRepository extends JpaRepository<VehicleEventEntity, Long> {
}
