package org.emulator.application.port;

import org.common.dto.CommonResponse;
import org.emulator.domain.OnInfo;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

public class VehicleCommandSenderTest {

	private final VehicleCommandSender vehicleCommandSender = new VehicleCommandSender() {
		@Override
		public CommonResponse sendOnCommand(OnInfo onInfo) {

			return new CommonResponse("000", "Success", "01234567890");
		}
	};

	@Test
	void sendOnTest() {
		LocalDateTime now = LocalDateTime.now();
		OnInfo onInfo = OnInfo.builder()
			.onTime(now)
			.gpsStatus("A")
			.totalDistance(5000)
			.build();
		CommonResponse response = vehicleCommandSender.sendOnCommand(onInfo);

		assertThat(response).isEqualTo(new CommonResponse("000", "Success", "01234567890"));
	}
}
