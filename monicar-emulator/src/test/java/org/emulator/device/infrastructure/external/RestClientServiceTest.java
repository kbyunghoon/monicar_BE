package org.emulator.device.infrastructure.external;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockserver.model.HttpRequest.*;
import static org.mockserver.model.HttpResponse.*;

import java.time.LocalDateTime;

import org.emulator.device.common.response.BaseResponse;
import org.emulator.device.common.response.SuccessCode;
import org.emulator.device.domain.GpsStatus;
import org.emulator.device.domain.OnInfo;
import org.emulator.device.infrastructure.external.command.OnCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.ObjectMapper;

@DisplayName("RestClientService 요청 테스트")
@SpringBootTest
class RestClientServiceTest {
	@Autowired
	private RestClientService restClientService;
	@Autowired
	private ObjectMapper mapper;

	private ClientAndServer mockServer;

	@BeforeEach
	public void setUp() {
		mockServer = ClientAndServer.startClientAndServer(8082);
	}

	@AfterEach
	public void tearDown() {
		mockServer.stop();
	}

	@DisplayName("post 성공 테스트")
	@Test
	void postKeyOn() throws Exception {
		//given
		OnCommand command = OnCommand.from(OnInfo.create(LocalDateTime.now(), GpsStatus.A, 20.111111, 30.111111, 5000));
		BaseResponse expected = BaseResponse.<Void>builder()
			.isSuccess(true)
			.message(SuccessCode.OK.getMessage())
			.build();

		mockServer.when(
			request()
				.withMethod("POST")
				.withPath("/api/v1/event-hub/key-on")
				.withBody(mapper.writeValueAsString(command)))
			.respond(
				response()
					.withStatusCode(200)
					.withBody(mapper.writeValueAsString(expected))
			);

		//when
		BaseResponse result = restClientService.post(
			"key-on",
			command
		);

		//then
		assertEquals(expected, result);
	}
}