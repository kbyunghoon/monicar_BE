package org.controlcenter.vehicle.infrastructure;

import java.util.List;
import java.util.Optional;

import org.controlcenter.vehicle.application.port.VehicleRepository;
import org.controlcenter.vehicle.domain.VehicleInformation;
import org.controlcenter.vehicle.domain.cluster.ClusterCreateCommand;
import org.controlcenter.vehicle.domain.cluster.GeoCoordinate;
import org.controlcenter.vehicle.infrastructure.jpa.VehicleInformationJpaRepository;
import org.controlcenter.vehicle.infrastructure.jpa.entity.VehicleInformationEntity;
import org.controlcenter.vehicle.infrastructure.mybatis.MyBatisVehicleInfoMapper;
import org.controlcenter.vehicle.infrastructure.mybatis.dto.GeoCoordinateDto;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class VehicleInformationRepositoryAdapter implements VehicleRepository {
	private final VehicleInformationJpaRepository vehicleInformationJpaRepository;
	private final MyBatisVehicleInfoMapper myBatisVehicleInfoMapper;

	@Override
	public Optional<VehicleInformation> findById(Long vehicleId) {
		return vehicleInformationJpaRepository.findById(vehicleId)
			.map(VehicleInformationEntity::toDomain);
	}

	@Override
	public Optional<VehicleInformation> findByMdn(Long mdn) {
		return vehicleInformationJpaRepository.findByMdn(mdn)
			.map(VehicleInformationEntity::toDomain);
	}

	@Override
	public List<GeoCoordinate> findCoordinatesByCompanyId(ClusterCreateCommand command, Long companyId) {
		return myBatisVehicleInfoMapper.findCoordinatesByCompanyId(
				companyId,
				command.getNe().getLat(),
				command.getNe().getLng(),
				command.getSw().getLat(),
				command.getSw().getLng()
			)
			.stream()
			.map(GeoCoordinateDto::toDomain)
			.toList();
	}

	@Override
	public Optional<VehicleInformation> findByVehicleNumber(String vehicleNumber) {
		return vehicleInformationJpaRepository.findByVehicleNumber(vehicleNumber)
			.map(VehicleInformationEntity::toDomain);
	}

}
