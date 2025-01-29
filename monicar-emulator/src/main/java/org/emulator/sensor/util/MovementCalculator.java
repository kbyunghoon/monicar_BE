package org.emulator.sensor.util;

import org.emulator.device.domain.CycleInfo;
import org.emulator.sensor.dto.GpsTime;

public interface MovementCalculator {
    Integer calculate(CycleInfo preInfo, GpsTime curInfo);
}
