package org.emulator.device.domain;

import org.emulator.device.application.port.TransmissionTimeProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Setter;

@Component
@Setter
public class EmulatorTransmissionTimeProvider implements TransmissionTimeProvider {
	@Value("${api.transmission-time}")
	private int transmissionTime;

	public void updateTransmissionTime(int transmissionTime) {
		this.transmissionTime = transmissionTime;
	}

	@Override
	public int getTransmissionTime() {
		return transmissionTime;
	}
}
