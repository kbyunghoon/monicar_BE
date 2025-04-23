package org.controlcenter.vehicle.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;

import org.controlcenter.vehicle.application.port.DrivingLogRepository;
import org.controlcenter.vehicle.application.port.VehicleRepository;
import org.controlcenter.vehicle.domain.DailyDrivingSummary;
import org.controlcenter.vehicle.domain.Period;
import org.controlcenter.vehicle.presentation.dto.DailyDrivingLogsResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
}