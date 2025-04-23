package org.controlcenter.vehicle.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.controlcenter.common.exception.BusinessException;
import org.controlcenter.history.infrastructure.jpa.DrivingHistoryJpaRepository;
import org.controlcenter.vehicle.application.port.VehicleEventRepository;
import org.controlcenter.vehicle.application.port.VehicleRepository;
import org.controlcenter.vehicle.domain.VehicleInformation;
import org.controlcenter.vehicle.domain.VehicleRegister;
import org.controlcenter.vehicle.infrastructure.elasticsearch.repository.VehicleInformationElasticsearchRepository;
import org.controlcenter.vehicle.infrastructure.jpa.VehicleInformationJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("[service 단위 테스트] VehicleService")
class VehicleServiceTest {
	private VehicleRepository vehicleRepository;
	private VehicleInformationJpaRepository vehicleInformationJpaRepository;
	private DrivingHistoryJpaRepository drivingHistoryJpaRepository;
	private VehicleInformationElasticsearchRepository vehicleInformationElasticsearchRepository;

	private VehicleService vehicleService;

	@BeforeEach
	void setUp() {
		vehicleRepository = mock(VehicleRepository.class);
		VehicleEventRepository vehicleEventRepository = mock(VehicleEventRepository.class);
		vehicleInformationJpaRepository = mock(VehicleInformationJpaRepository.class);
		drivingHistoryJpaRepository = mock(DrivingHistoryJpaRepository.class);
		vehicleInformationElasticsearchRepository = mock(VehicleInformationElasticsearchRepository.class);

		vehicleService = new VehicleService(
			vehicleRepository,
			vehicleEventRepository,
			vehicleInformationJpaRepository,
			drivingHistoryJpaRepository,
			vehicleInformationElasticsearchRepository
		);
	}

	@DisplayName("차량 정보 조회 테스트")
	@Test
	void getVehicleInformation_success() {
		// Given
		Long vehicleId = 1L;
		VehicleInformation vehicleInfo = mock(VehicleInformation.class);
		when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.of(vehicleInfo));

		// When
		VehicleInformation result = vehicleService.getVehicleInformation(vehicleId);

		// Then
		assertThat(result).isEqualTo(vehicleInfo);
	}

	@DisplayName("없는 차량 ID로 조회 시 예외 발생 테스트")
	@Test
	void getVehicleInformation_notFound() {
		// Given
		Long vehicleId = 999L;
		when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.empty());

		// When & Then
		assertThrows(BusinessException.class, () -> vehicleService.getVehicleInformation(vehicleId));
	}

	@DisplayName("이미 존재하는 차량 번호로 등록하면 예외 발생 테스트")
	@Test
	void register_alreadyExistVehicleNumber() {
		// Given
		String vehicleNumber = "123가4567";
		VehicleRegister command = mock(VehicleRegister.class);
		when(command.getVehicleNumber()).thenReturn(vehicleNumber);
		when(vehicleRepository.findByVehicleNumber(vehicleNumber)).thenReturn(
			Optional.of(mock(VehicleInformation.class)));

		// When & Then
		assertThrows(BusinessException.class, () -> vehicleService.register(command));
	}

	@DisplayName("차량 등록 테스트")
	@Test
	void register_success() {
		// Given
		String vehicleNumber = "123가4567";
		Long vehicleTypeId = 1L;
		LocalDate deliveryDate = LocalDate.now();
		int drivingDistance = 100;

		VehicleRegister command = VehicleRegister.builder()
			.vehicleTypeId(vehicleTypeId)
			.vehicleNumber(vehicleNumber)
			.drivingDistance(drivingDistance)
			.deliveryDate(deliveryDate)
			.build();

		when(vehicleRepository.findByVehicleNumber(vehicleNumber)).thenReturn(Optional.empty());

		VehicleInformation expected = VehicleInformation.create(command);
		when(vehicleRepository.save(any())).thenReturn(expected);

		// When
		VehicleInformation result = vehicleService.register(command);

		// Then
		assertThat(result).isEqualTo(expected);
		assertThat(result.getVehicleNumber()).isEqualTo(vehicleNumber);
		assertThat(result.getVehicleTypeId()).isEqualTo(vehicleTypeId);
		assertThat(result.getDeliveryDate()).isEqualTo(deliveryDate);
		assertThat(result.getSum()).isEqualTo(drivingDistance);
	}


	@DisplayName("차량 삭제 테스트")
	@Test
	void deleteVehicle_success() {
		// Given
		Long vehicleId = 1L;
		when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.of(mock(VehicleInformation.class)));

		// When
		vehicleService.deleteVehicle(vehicleId);

		// Then
		verify(vehicleInformationElasticsearchRepository).deleteById(vehicleId);
		verify(vehicleInformationJpaRepository).softDeleteById(eq(vehicleId), any(LocalDateTime.class));
		verify(drivingHistoryJpaRepository).softDeleteById(eq(vehicleId), any(LocalDateTime.class));
	}
}