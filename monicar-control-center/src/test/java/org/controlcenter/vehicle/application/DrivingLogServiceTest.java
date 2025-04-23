package org.controlcenter.vehicle.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.controlcenter.common.exception.BusinessException;
import org.controlcenter.vehicle.application.port.DrivingLogRepository;
import org.controlcenter.vehicle.application.port.VehicleRepository;
import org.controlcenter.vehicle.domain.DailyDrivingSummary;
import org.controlcenter.vehicle.domain.DrivingLog;
import org.controlcenter.vehicle.domain.DrivingLogDetailsContent;
import org.controlcenter.vehicle.domain.HourlyDrivingLogs;
import org.controlcenter.vehicle.domain.Period;
import org.controlcenter.vehicle.domain.VehicleHeaderInfo;
import org.controlcenter.vehicle.domain.VehicleInformation;
import org.controlcenter.vehicle.domain.VehicleSortType;
import org.controlcenter.vehicle.domain.VehicleStatus;
import org.controlcenter.vehicle.presentation.dto.DailyDrivingLogsResponse;
import org.controlcenter.vehicle.presentation.dto.VehicleDrivingLogDetailsResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@DisplayName("[service 단위 테스트] DrivingLogService")
class DrivingLogServiceTest {
	private DrivingLogRepository drivingLogRepository;
	private VehicleRepository vehicleRepository;
	private VehicleService vehicleService;

	private DrivingLogService drivingLogService;

	@BeforeEach
	void setUp() {
		drivingLogRepository = mock(DrivingLogRepository.class);
		vehicleRepository = mock(VehicleRepository.class);
		vehicleService = mock(VehicleService.class);

		drivingLogService = new DrivingLogService(drivingLogRepository, vehicleRepository, vehicleService);
	}

	@DisplayName("일별 주행 요약 조회 테스트")
	@Test
	void getDailySummaries_success() {
		// Given
		Long vehicleId = 1L;
		Period period = Period.WEEK;
		List<DailyDrivingSummary> summaries = Collections.emptyList();

		when(drivingLogRepository.getDailySummaries(anyLong(), any(), any()))
			.thenReturn(summaries);
		when(vehicleService.getVehicleNumber(vehicleId))
			.thenReturn("123가4567");

		// When
		DailyDrivingLogsResponse result = drivingLogService.getDailySummaries(vehicleId, period);

		// Then
		assertThat(result).isNotNull();
		assertThat(result.vehicleNumber()).isEqualTo("123가4567");
		assertThat(result.content()).isEqualTo(summaries);
	}

	@DisplayName("시간별 주행 요약 조회 테스트")
	@Test
	void getHourlySummaries_success() {
		// Given
		Long vehicleId = 1L;
		LocalDate date = LocalDate.now();
		List<HourlyDrivingLogs> hourlyLogs = List.of(mock(HourlyDrivingLogs.class));

		when(drivingLogRepository.getHourlyDrivingLogs(vehicleId, date))
			.thenReturn(hourlyLogs);

		// When
		List<HourlyDrivingLogs> result = drivingLogService.getHourlySummaries(vehicleId, date);

		// Then
		assertThat(result).isEqualTo(hourlyLogs);
	}

	@DisplayName("주행 로그 리스트를 조회 테스트")
	@Test
	void getDrivingLogList_success() {
		// Given
		String vehicleNumber = "123가4567";
		Pageable pageable = PageRequest.of(0, 10);
		Page<DrivingLog> drivingLogs = new PageImpl<>(List.of(mock(DrivingLog.class)));

		when(drivingLogRepository.findByVehicleNumber(vehicleNumber, VehicleSortType.CREATED_AT_DESC, pageable))
			.thenReturn(drivingLogs);

		// When
		Page<DrivingLog> result = drivingLogService.getDrivingLogList(vehicleNumber, VehicleSortType.CREATED_AT_DESC,
			pageable);

		// Then
		assertThat(result.getContent()).hasSize(1);
	}

	@DisplayName("차량 주행 상세 내역 조회 테스트")
	@Test
	void getVehicleDrivingLogDetails_success() {
		// Given
		Long vehicleId = 1L;
		LocalDate start = LocalDate.of(2023, 1, 1);
		LocalDate end = LocalDate.of(2023, 12, 31);

		VehicleHeaderInfo header = VehicleHeaderInfo.builder()
			.vehicleNumber("123가4567")
			.vehicleTypesName("소나타")
			.status(VehicleStatus.NOT_DRIVEN)
			.companyName("카카오")
			.businessRegistrationNumber("111-11-11111")
			.build();

		when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.of(mock(VehicleInformation.class)));
		when(drivingLogRepository.findVehicleHeaderInfoByVehicleId(vehicleId)).thenReturn(header);
		when(drivingLogRepository.findDrivingLogsByVehicleIdAndDateRange(eq(vehicleId), any(), any()))
			.thenReturn(List.of(mock(DrivingLogDetailsContent.class)));
		when(drivingLogRepository.sumByVehicleIdAndDateRange(eq(vehicleId), any(), any()))
			.thenReturn(1000);

		// When
		VehicleDrivingLogDetailsResponse result = drivingLogService.getVehicleDrivingLogDetails(vehicleId, start, end);

		// Then
		assertThat(result).isNotNull();
		assertThat(result.vehicleInfo().getVehicleNumber()).isEqualTo("123가4567");
		assertThat(result.businessInfo().getBusinessName()).isEqualTo("카카오");
		assertThat(result.taxPeriodDistance()).isEqualTo(1000);
	}

	@DisplayName("시작일이 종료일보다 이후일 경우 예외 발생 테스트")
	@Test
	void getVehicleDrivingLogDetails_invalidDate() {
		// Given
		Long vehicleId = 1L;
		LocalDate start = LocalDate.of(2025, 1, 1);
		LocalDate end = LocalDate.of(2024, 1, 1);

		// When & Then
		assertThrows(BusinessException.class,
			() -> drivingLogService.getVehicleDrivingLogDetails(vehicleId, start, end));
	}
}