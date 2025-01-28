package org.controlcenter.vehicle.infrastructure.jpa;

import java.time.LocalDateTime;
import java.util.Optional;

import org.controlcenter.vehicle.infrastructure.jpa.entity.VehicleInformationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VehicleInformationJpaRepository extends JpaRepository<VehicleInformationEntity, Long> {
	Optional<VehicleInformationEntity> findByMdn(Long mdn);
	Optional<VehicleInformationEntity> findByVehicleNumber(String vehicleNumber);

	@Modifying
	@Query("UPDATE vehicle_information v SET v.deletedAt = :deletedAt WHERE v.id = :id")
	void softDeleteById(@Param("id") Long id, @Param("deletedAt") LocalDateTime deletedAt);
}
