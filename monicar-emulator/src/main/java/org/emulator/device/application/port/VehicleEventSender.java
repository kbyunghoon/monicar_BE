package org.emulator.device.application.port;

import org.common.dto.CommonResponse;
import org.emulator.device.domain.CycleInfo;
import org.emulator.device.domain.OnInfo;

import java.util.List;

/**
 * 차량 서버에 명령을 보내는 역할을 선언한다.
 */
public interface VehicleEventSender {
	CommonResponse sendOnEvent(OnInfo onInfo);
	CommonResponse sendCycleInfoEvent(List<CycleInfo> cycleInfo);
}
