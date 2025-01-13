package org.producer.producer;

import org.producer.dto.CycleInfoListCommand;

public interface CycleInfoEventPublisher {
	void publishEvent(CycleInfoListCommand cycleInfoListCommand);
}
