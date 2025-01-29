package org.emulator.device.application.port;

import org.common.dto.CommonResponse;
import org.emulator.device.common.response.BaseResponse;
import org.emulator.device.domain.CycleInfo;
import org.emulator.device.domain.OnInfo;

import java.util.List;

/**
 * 차량 서버에 명령을 보내는 역할을 선언한다.
 */
public interface VehicleEventSender {
	BaseResponse sendOnEvent(OnInfo onInfo);
	BaseResponse sendCycleInfoEvent(List<CycleInfo> cycleInfo);
}
