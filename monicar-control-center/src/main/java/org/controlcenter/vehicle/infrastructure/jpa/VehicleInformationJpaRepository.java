package org.controlcenter.vehicle.infrastructure.jpa;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.controlcenter.vehicle.domain.Cluster;
import org.controlcenter.vehicle.domain.ClusterDetail;
import org.controlcenter.vehicle.domain.VehicleStatus;
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

	@Query("SELECT new org.controlcenter.vehicle.domain.Cluster(AVG(vi.lat), AVG(vi.lng), COUNT(vi)) " +
		"FROM vehicle_information vi " +
		"WHERE (vi.lat IS NOT null and vi.lng IS NOT null)" +
		"AND vi.lat BETWEEN :swLat AND :neLat " +
		"AND vi.lng BETWEEN :swLng AND :neLng " +
		"AND (:status IS NULL OR vi.status = :status) " +
		"GROUP BY FUNCTION('floor', (vi.lat - :swLat) / :gridLat), FUNCTION('floor', (vi.lng - :swLng) / :gridLng)")
	List<Cluster> findClusters(
		@Param("swLat") int swLat,
		@Param("neLat") int neLat,
		@Param("swLng") int swLng,
		@Param("neLng") int neLng,
		@Param("gridLat") int gridLat,
		@Param("gridLng") int gridLng,
		@Param("status") VehicleStatus status);

	@Query("SELECT new org.controlcenter.vehicle.domain.ClusterDetail(vi.id, vi.vehicleNumber, vi.lat, vi.lng) " +
		"FROM vehicle_information vi " +
		"WHERE (vi.lat IS NOT null and vi.lng IS NOT null)" +
		"AND vi.lat BETWEEN :swLat AND :neLat " +
		"AND vi.lng BETWEEN :swLng AND :neLng " +
		"AND (:status IS NULL OR vi.status = :status)")
	List<ClusterDetail> findClustersDetail(
		@Param("swLat") int swLat,
		@Param("neLat") int neLat,
		@Param("swLng") int swLng,
		@Param("neLng") int neLng,
		@Param("status") VehicleStatus status);
}
