package org.emulator.device.presentation;

import static org.hamcrest.core.StringContains.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.common.dto.CommonResponse;
import org.emulator.device.application.VehicleService;
import org.emulator.device.common.response.BaseResponse;
import org.emulator.sensor.GpsSensor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@DisplayName("에뮬레이터 VehicleApiController 테스트")
@WebMvcTest(VehicleApiController.class)
class VehicleApiControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private GpsSensor gpsSensor;

	@MockBean
	private VehicleService vehicleService;

	@DisplayName("on 요청 success 테스트")
	@Test
	void keyOnSuccess() throws Exception {
		when(vehicleService.onVehicle()).thenReturn(BaseResponse.success());

		var result = mockMvc.perform(post("/api/v1/emulator/key-on")
				.accept(MediaType.APPLICATION_JSON)
			);

		result.andDo(print());
		result.andExpect(status().isOk());
	}

	@DisplayName("on 요청 fail 테스트")
	@Test
	void keyOnFail() throws Exception {
		CommonResponse mockedFailResponse = new CommonResponse("100", "Invalid access path.", "01234567890");

		when(vehicleService.onVehicle()).thenReturn(BaseResponse.fail(mockedFailResponse));

		var result = mockMvc.perform(post("/api/v1/emulator/key-on")
			.accept(MediaType.APPLICATION_JSON)
		);

		result.andDo(print());
		result.andExpect(content().string(containsString("Invalid access path.")));
	}
}