package org.controlcenter.alarm.application.port;

import java.util.Optional;

import org.controlcenter.alarm.domain.Alarm;

public interface AlarmRepository {
	Alarm save(Alarm alarm);

	Optional<Alarm> findById(long alarmId);
}
