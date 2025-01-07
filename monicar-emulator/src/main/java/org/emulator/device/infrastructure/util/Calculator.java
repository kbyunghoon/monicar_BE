package org.emulator.device.infrastructure.util;

import org.emulator.device.domain.CycleInfo;
import org.emulator.device.infrastructure.GpsTime;

public interface Calculator {
    Integer calculate(CycleInfo preInfo, GpsTime curInfo);
}
