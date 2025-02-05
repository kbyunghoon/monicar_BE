package org.eventhub.application;

import org.eventhub.application.port.DrivingHistoryRepository;
import org.eventhub.domain.DrivingHistory;
import org.eventhub.domain.DrivingHistoryCreate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DrivingHistoryService {
	private final DrivingHistoryRepository drivingHistoryRepository;

    public DrivingHistory saveDrivingHistory(DrivingHistoryCreate drivingHistoryCreateDto) {
		return drivingHistoryRepository.save(DrivingHistory.of(drivingHistoryCreateDto));
    }
}
