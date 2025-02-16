package org.eventhub.application.port;

import org.eventhub.infrastructure.external.command.AlarmSend;

public interface AlarmSender {
	void sendAlarm(AlarmSend alarmSend);
}
