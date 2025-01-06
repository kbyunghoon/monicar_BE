package org.emulator.device.infrastructure.external.command.vo;

import lombok.Getter;

@Getter
public class Speed {
	private static final int SPEED_MIN = 0;
	private static final int SPEED_MAX = 255;

	private final int value;

	public Speed() {
		this.value = 0;
	}

	public Speed(int value) {
		this.value = Math.clamp(value, SPEED_MIN, SPEED_MAX);
	}
}
