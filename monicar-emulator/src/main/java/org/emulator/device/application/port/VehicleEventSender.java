package org.emulator.device.application.port;

import java.util.List;

import org.emulator.device.common.response.BaseResponse;
import org.emulator.device.domain.CycleInfo;
import org.emulator.device.domain.OffInfo;
import org.emulator.device.domain.OnInfo;

/**
 * 차량 서버에 명령을 보내는 역할을 선언한다.
 */
public interface VehicleEventSender {
	BaseResponse sendOnEvent(OnInfo onInfo);
	BaseResponse sendOffEvent(OffInfo offInfo);
	BaseResponse sendCycleInfoEvent(List<CycleInfo> cycleInfo);
}
