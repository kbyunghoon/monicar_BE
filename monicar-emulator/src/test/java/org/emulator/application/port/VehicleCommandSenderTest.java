package org.emulator.application.port;

import org.common.dto.CommonResponse;
import org.emulator.domain.OnInfo;
import org.junit.jupiter.api.Test;

public class VehicleCommandSenderTest {

	private final VehicleCommandSender vehicleCommandSender = new VehicleCommandSender() {
		@Override
		public CommonResponse sendOnCommand(OnInfo onInfo) {
			return null;
		}
	};

	@Test
	void sendOnTest() {}
}
