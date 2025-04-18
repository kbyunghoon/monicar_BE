package org.rabbitmqcollector.location.infrastructure.jpa.entity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VehicleJpaRepository extends JpaRepository<VehicleInformationEntity, Long> {
	@Query("SELECT v.vehicleNumber FROM vehicle_information v WHERE v.id = :id")
	Optional<String> findVehicleNumberById(@Param("id") Long id);
}
