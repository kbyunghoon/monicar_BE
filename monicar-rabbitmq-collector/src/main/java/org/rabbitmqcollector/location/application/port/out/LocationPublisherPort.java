package org.rabbitmqcollector.location.application.port.out;

import java.util.List;

import org.rabbitmqcollector.location.presentation.dto.CarLocationMessage;

public interface LocationPublisherPort {
	void publishLocation(long carId, List<CarLocationMessage> message);
}

