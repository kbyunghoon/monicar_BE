package org.eventhub.infrastructure.external.command;

import lombok.Builder;

@Builder
public record AlarmSend(Long alarmId) {}
