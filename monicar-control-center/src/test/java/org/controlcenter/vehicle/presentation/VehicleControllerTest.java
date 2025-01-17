package org.controlcenter.vehicle.presentation;

import static org.hamcrest.core.StringContains.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.controlcenter.cycleinfo.domain.GpsStatus;
import org.controlcenter.vehicle.application.VehicleEventService;
import org.controlcenter.vehicle.application.VehicleService;
import org.controlcenter.vehicle.domain.VehicleEvent;
import org.controlcenter.vehicle.domain.VehicleEventType;
import org.controlcenter.vehicle.domain.VehicleInformation;
import org.controlcenter.vehicle.infrastructure.VehicleQueryRepository;
import org.controlcenter.vehicle.presentation.dto.KeyOnRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@DisplayName("VehicleController 테스트")
@WebMvcTest(VehicleController.class)
class VehicleControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private VehicleQueryRepository vehicleQueryRepository;
	@MockBean
	private VehicleService vehicleService;
	@MockBean
	private VehicleEventService vehicleEventService;

	@DisplayName("on 요청 success 테스트")
	@Test
	void keyOn_Success() throws Exception {
		long vehicleInformationId = 1L;
		long vehicleEventId = 2L;
		long mdn = 1234567890L;
		int sum = 999;
		when(vehicleService.getVehicleInformation(mdn))
			.thenReturn(VehicleInformation.builder()
				.id(vehicleInformationId)
				.sum(sum)
				.build()
			);

		when(vehicleEventService.getRecentVehicleEvent(vehicleInformationId))
			.thenReturn(VehicleEvent.builder()
				.id(vehicleEventId)
				.type(VehicleEventType.OFF)
				.build()
			);

		String json = new ObjectMapper().writeValueAsString(new KeyOnRequest(mdn, "1111", 11, 1, 111, "20250101080808", "20250101080808", GpsStatus.P, BigDecimal.valueOf(20203030), BigDecimal.valueOf(20203030), 2, 2, 999));
		var result = mockMvc.perform(post("/api/v1/vehicles/key-on")
			.accept(MediaType.APPLICATION_JSON)
			.contentType(MediaType.APPLICATION_JSON)
			.content(json)
		);


		result.andDo(print());
		result.andExpect(status().isOk());
	}

	@DisplayName("on 요청 fail 테스트-이미 on인 상태일 때")
	@Test
	void keyOn_fail() throws Exception {
		long vehicleInformationId = 1L;
		long vehicleEventId = 2L;
		long mdn = 1234567890L;
		int sum = 999;
		when(vehicleService.getVehicleInformation(mdn))
			.thenReturn(VehicleInformation.builder()
				.id(vehicleInformationId)
				.sum(sum)
				.build()
			);

		when(vehicleEventService.getRecentVehicleEvent(vehicleInformationId))
			.thenReturn(VehicleEvent.builder()
				.id(vehicleEventId)
				.type(VehicleEventType.ON)
				.build()
			);

		String json = new ObjectMapper().writeValueAsString(new KeyOnRequest(mdn, "1111", 11, 1, 111, "20250101080808", "20250101080808", GpsStatus.P, BigDecimal.valueOf(20203030), BigDecimal.valueOf(20203030), 2, 2, 999));
		var result = mockMvc.perform(post("/api/v1/vehicles/key-on")
			.accept(MediaType.APPLICATION_JSON)
			.contentType(MediaType.APPLICATION_JSON)
			.content(json)
		);


		result.andDo(print());
		result.andExpect(content().string(containsString("This is the wrong approach.")));
	}
}