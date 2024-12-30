package org.emulator.device.infrastructure.external;

import java.util.Map;

import org.common.dto.CommonResponse;
import org.emulator.device.application.port.VehicleCommandSender;
import org.emulator.device.common.HeaderName;
import org.emulator.device.common.HeaderUtils;
import org.emulator.device.domain.OnInfo;
import org.emulator.device.infrastructure.external.command.OnCommand;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class RestClientVehicleCommandSender implements VehicleCommandSender {
	private final RestClientService restClientService;

	public CommonResponse sendOnCommand(OnInfo onInfo) {
		RestClient restClient = restClientService.getRestClient(UrlPathEnum.CONTROL_CENTER);

		// body: onInfo - onCommand 매핑
		OnCommand onCommand = OnCommand.of(onInfo);
		Map<String, String> headers = HeaderUtils.additionalHeaders(HeaderName.TIMESTAMP, HeaderName.TUID);

		return restClientService.post(
			restClient,
			onCommand,
			headers,
			CommonResponse.class
		);
	}
}
