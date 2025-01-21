package org.emulator.device.infrastructure.external;

import java.util.List;
import java.util.Optional;

import org.common.dto.CommonResponse;
import org.emulator.device.application.port.VehicleEventSender;
import org.emulator.device.common.exception.BusinessException;
import org.emulator.device.common.response.ErrorCode;
import org.emulator.device.domain.CycleInfo;
import org.emulator.device.domain.OnInfo;
import org.emulator.device.infrastructure.external.command.CycleInfoListCommand;
import org.emulator.device.infrastructure.external.command.OnCommand;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Component
public class VehicleEventRestClient implements VehicleEventSender {
	private static final String RESPONSE_SUCCESS_CODE = "000";
	private final RestClientService restClientService;

	public CommonResponse sendOnEvent(OnInfo onInfo) {
		OnCommand onCommand = OnCommand.from(onInfo);

		return handleResponse(restClientService.post("key-on", onCommand));
	}

	@Override
	public CommonResponse sendCycleInfoEvent(List<CycleInfo> cycleInfo) {
		CycleInfoListCommand cycleInfoListCommand = CycleInfoListCommand.from(cycleInfo);

		return handleResponse(restClientService.post("cycle-info", cycleInfoListCommand));
	}

	private CommonResponse handleResponse(Optional<CommonResponse> optionalResponse) {
		return optionalResponse
			.filter(response -> isSuccess(response.rstCd()))
			.orElseThrow(() -> new BusinessException(ErrorCode.UNSUPPORTED_RESPONSE));
	}

	private boolean isSuccess(String responseCode) {
		return RESPONSE_SUCCESS_CODE.equals(responseCode);
	}
}
