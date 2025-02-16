package org.controlcenter.alarm.application;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.controlcenter.alarm.application.port.AlarmRepository;
import org.controlcenter.alarm.domain.Alarm;
import org.controlcenter.alarm.domain.AlarmCreate;
import org.controlcenter.alarm.domain.AlarmInfo;
import org.controlcenter.alarm.domain.AlarmStatus;
import org.controlcenter.alarm.domain.AlarmStatusStats;
import org.controlcenter.alarm.domain.SendAlarm;
import org.controlcenter.alarm.infrastructure.jpa.AlarmJpaRepository;
import org.controlcenter.alarm.infrastructure.jpa.entity.AlarmEntity;
import org.controlcenter.alarm.presentation.dto.AlarmStatusStatsResponse;
import org.controlcenter.common.exception.BusinessException;
import org.controlcenter.common.response.code.ErrorCode;
import org.controlcenter.company.application.port.ManagerRepository;
import org.controlcenter.vehicle.application.port.VehicleRepository;
import org.controlcenter.vehicle.domain.VehicleInformation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlarmService {
	private final Map<String, SseEmitter> sseEmitters = new ConcurrentHashMap<>();
	private final AlarmRepository alarmRepository;
	private final AlarmJpaRepository alarmJpaRepository;
	private final VehicleRepository vehicleRepository;
	private final ManagerRepository managerRepository;

	/**
	 * 특정 userId가 SSE 구독을 시작할 때 호출.
	 */
	public SseEmitter subscribe(String userId) {
		SseEmitter sseEmitter = new SseEmitter(0L);

		sseEmitters.put(userId, sseEmitter);

		sseEmitter.onCompletion(() -> sseEmitters.remove(userId));

		sseEmitter.onTimeout(() -> sseEmitters.remove(userId));

		sseEmitter.onError(event -> sseEmitters.remove(userId));

		return sseEmitter;
	}

	@Transactional(readOnly = true)
	public Page<AlarmInfo> listAlarmStatus(AlarmStatus status, Pageable pageable) {
		return alarmJpaRepository.findAlarmListByStatus(status, pageable);
	}

	@Transactional
	public void nextStep(String managerId, long alarmId) {
		AlarmEntity alarmEntity = alarmJpaRepository.findByIdAndIsCheckedFalse(alarmId)
			.orElseThrow(() -> new BusinessException(ErrorCode.FORBIDDEN_ACCESS));

		Alarm previousAlarm = alarmEntity.toDomain();
		AlarmStatus currentStatus = previousAlarm.getStatus();

		if (currentStatus == AlarmStatus.COMPLETED) {
			throw new BusinessException(ErrorCode.FORBIDDEN_ACCESS);
		}

		AlarmStatus nextStatus = currentStatus.next();

		VehicleInformation vehicleInformation = vehicleRepository.findById(previousAlarm.getVehicleId())
			.orElseThrow(() -> new BusinessException(ErrorCode.VEHICLE_NOT_FOUND));
		Integer drivingDistance = vehicleInformation.getSum();

		alarmEntity.checked();

		var alarmCreate = AlarmCreate.builder()
			.managerId(managerId)
			.vehicleId(previousAlarm.getVehicleId())
			.drivingDistance(drivingDistance)
			.status(nextStatus)
			.build();

		var alarm = alarmRepository.save(Alarm.create(alarmCreate));

		String managerName = managerRepository.getUserProfile(managerId).getNickname();

		SendAlarm newAlarm = SendAlarm.builder()
			.id(alarm.getId())
			.vehicleNumber(vehicleInformation.getVehicleNumber())
			.drivingDistance(nextStatus == AlarmStatus.COMPLETED ? vehicleInformation.getSum() : null)
			.managerName(managerName)
			.status(nextStatus)
			.build();

		sendAll(newAlarm);
	}

	public void newAlarmRequest(long alarmId) {
		AlarmEntity alarmEntity = alarmJpaRepository.findByIdAndIsCheckedFalse(alarmId)
			.orElseThrow(() -> new BusinessException(ErrorCode.FORBIDDEN_ACCESS));

		var alarm = alarmEntity.toDomain();
		VehicleInformation vehicleInformation = vehicleRepository.findById(alarm.getVehicleId())
			.orElseThrow(() -> new BusinessException(ErrorCode.VEHICLE_NOT_FOUND));

		SendAlarm newAlarm = SendAlarm.builder()
			.id(alarm.getId())
			.vehicleNumber(vehicleInformation.getVehicleNumber())
			.drivingDistance(vehicleInformation.getSum())
			.status(alarm.getStatus())
			.build();

		sendAll(newAlarm);
	}

	/**
	 * 특정 userId에게 메시지를 전송(추후 사용 대비)
	 */
	public void sendMessageToUser(String userId, String message) {
		SseEmitter emitter = sseEmitters.get(userId);

		if (emitter != null) {
			try {
				emitter.send(
					SseEmitter.event()
						.name("custom-event")
						.data(message)
				);
			} catch (IOException e) {
				sseEmitters.remove(userId);
			}
		}
	}

	public void sendAll(SendAlarm sendAlarm) {
		sseEmitters.forEach((userId, emitter) -> {
			try {
				emitter.send(
					SseEmitter.event()
						.name(String.valueOf(sendAlarm.getStatus()))
						.data(sendAlarm, MediaType.APPLICATION_JSON)
				);
			} catch (IOException e) {
				sseEmitters.remove(userId);
			}
		});
	}

	@Transactional(readOnly = true)
	public List<AlarmStatusStatsResponse> getAlarmStatusCounts() {
		List<AlarmStatusStats> rawData = alarmJpaRepository.findStatusCounts();
		return rawData.stream()
			.map(arr -> new AlarmStatusStatsResponse(
				convertToKoreanName(arr.getName()),
				arr.getCount()
			)).toList();
	}

	// AlarmStatus -> 한글명 맵핑
	private String convertToKoreanName(AlarmStatus status) {
		return switch (status) {
			case REQUIRED -> "점검요구";
			case SCHEDULED -> "점검예정";
			case INPROGRESS -> "점검진행중";
			case COMPLETED -> "점검완료";
		};
	}
}
