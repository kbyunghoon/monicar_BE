package org.emulator.sensor.util;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Setter;

@Component
@Getter
@Setter
public class CycleTimeProvider {
	@Value("${api.transmission-time}")
	private int cycleTime;
}
