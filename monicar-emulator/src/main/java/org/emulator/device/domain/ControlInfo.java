package org.emulator.device.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ControlInfo {
	private final String controlId;
	private final String controlCode;
	private final String cycleInfoTransmitTerm;
}
