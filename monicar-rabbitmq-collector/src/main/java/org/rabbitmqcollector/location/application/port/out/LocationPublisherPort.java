package org.rabbitmqcollector.location.application.port.out;

import org.rabbitmqcollector.location.presentation.dto.CarLocationSocketMessage;

public interface LocationPublisherPort {
	void publishLocation(long carId, CarLocationSocketMessage message);
}

