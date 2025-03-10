package org.emulator.device.application.port;

import org.emulator.device.domain.CycleInfo;

public interface GpsSseSender {
	void sendGpsTransmissionStatus(String message);

	void sendGps(CycleInfo cycleInfo);
}
