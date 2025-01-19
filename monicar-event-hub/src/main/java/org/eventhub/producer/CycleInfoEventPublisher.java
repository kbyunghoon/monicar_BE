package org.eventhub.producer;

import org.eventhub.dto.CycleInfoListCommand;

public interface CycleInfoEventPublisher {
	void publishEvent(CycleInfoListCommand cycleInfoListCommand);
}
