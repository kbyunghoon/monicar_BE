package org.rabbitmqcollector.location.infrastructure.jpa.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleJpaRepository extends JpaRepository<VehicleInformationEntity, Long> {
}
