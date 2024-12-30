package org.emulator.device.application.port;

import org.common.dto.CommonResponse;
import org.emulator.device.domain.OnInfo;

/**
 * 차량 서버에 명령을 보내는 역할
 */
public interface VehicleCommandSender {
	CommonResponse sendOnCommand(OnInfo onInfo);
}
