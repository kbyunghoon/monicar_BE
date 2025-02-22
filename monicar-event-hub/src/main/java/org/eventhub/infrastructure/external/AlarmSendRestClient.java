package org.eventhub.infrastructure.external;

import org.eventhub.application.port.AlarmSender;
import org.eventhub.infrastructure.external.command.AlarmSend;
import org.eventhub.infrastructure.external.util.HeaderName;
import org.eventhub.infrastructure.external.util.RestClientService;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class AlarmSendRestClient implements AlarmSender {
	private final RestClientService restClientService;

	@Override
	public void sendAlarm(AlarmSend alarmSend) {
		String endpoint = alarmSend.alarmId() + "/send";

		restClientService.postAlarm(endpoint, HeaderName.X_API_KEY);
	}
}
