package org.emulator.infrastructure.external;

import java.util.HashMap;
import java.util.Map;

import org.common.dto.CommonResponse;
import org.emulator.application.port.VehicleCommandSender;
import org.emulator.common.RequestUtil;
import org.emulator.domain.OnInfo;
import org.emulator.infrastructure.external.command.OnCommand;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class RestClientVehicleCommandSender implements VehicleCommandSender {
	private final RestClientService restClientService;

	public CommonResponse sendOnCommand(OnInfo onInfo) {
		RestClient restClient = restClientService.getRestClient(UrlPathEnum.CONTROL_CENTER);

		Map<String, String> headers = new HashMap<>();
		headers.put("Timestamp", RequestUtil.getCurrentTimestamp());
		headers.put("TUID", RequestUtil.generateTUID());

		// body: onInfo - onCommand 매핑
		OnCommand onCommand = OnCommand.of(onInfo);

		return restClientService.post(
			restClient,
			onCommand,
			headers,
			CommonResponse.class
		);
	}
}
