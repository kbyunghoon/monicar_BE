package org.rabbitmqcollector.location.application.port.out;

import org.rabbitmqcollector.location.presentation.dto.CarLocationMessage;

public interface LocationPublisherPort {
	void publishLocation(long carId, CarLocationMessage message);
}

