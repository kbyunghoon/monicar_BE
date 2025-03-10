package org.controlcenter.alarm.application.port;

import org.controlcenter.alarm.domain.Alarm;

public interface AlarmRepository {
	Alarm save(Alarm alarm);
}
