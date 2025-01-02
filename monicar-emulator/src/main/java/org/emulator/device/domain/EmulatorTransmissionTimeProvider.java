package org.emulator.device.domain;

import org.emulator.device.application.port.TransmissionTimeProvider;
import org.springframework.stereotype.Component;

@Component
public class EmulatorTransmissionTimeProvider implements TransmissionTimeProvider {
	private int transmissionTime = 60;

	public void updateTransmissionTime(int transmissionTime) {
		this.transmissionTime = transmissionTime;
	}

	@Override
	public int getTransmissionTime() {
		return transmissionTime;
	}
}
