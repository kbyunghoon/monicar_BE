package org.eventhub.application.port;

import org.eventhub.domain.DrivingHistory;

public interface DrivingHistoryRepository {
	DrivingHistory save(DrivingHistory drivingHistory);
}
