package org.controlcenter.alarm.presentation;

import org.controlcenter.alarm.application.AlarmService;
import org.controlcenter.alarm.domain.AlarmStatus;
import org.controlcenter.alarm.presentation.dto.AlarmResponse;
import org.controlcenter.alarm.presentation.swagger.AlarmApi;
import org.controlcenter.common.exception.BusinessException;
import org.controlcenter.common.response.BaseResponse;
import org.controlcenter.common.response.PageResponse;
import org.controlcenter.common.response.code.ErrorCode;
import org.controlcenter.common.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/alarm")
public class AlarmController implements AlarmApi {
	@Value("${alarm.secret-key}")
	private String alarmSecretKey;

	private final AlarmService alarmService;

	@GetMapping("/subscribe")
	@PreAuthorize("hasRole('ROLE_USER')")
	public SseEmitter subscribe(@AuthenticationPrincipal CustomUserDetails user) {
		return alarmService.subscribe(user.getId());
	}

	@PatchMapping("/{alarm-id}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public void next(
		@AuthenticationPrincipal CustomUserDetails user,
		@Valid @PathVariable(name = "alarm-id") Long alarmId
	) {
		alarmService.nextStep(user.getId(), alarmId);
	}

	@PostMapping("/{alarm-id}/send")
	public void send(
		@RequestHeader("X-API-KEY") String apiKey,
		@Valid @PathVariable(name = "alarm-id") Long alarmId
	) {
		if (!alarmSecretKey.equals(apiKey)) {
			throw new BusinessException(ErrorCode.FORBIDDEN_ACCESS);
		}

		alarmService.newAlarmRequest(alarmId);
	}

	@GetMapping
	@PreAuthorize("hasRole('ROLE_USER')")
	public BaseResponse<PageResponse<AlarmResponse>> list(
		@RequestParam(required = false) AlarmStatus status,
		@PageableDefault(size = 8) Pageable pageable
	) {
		var alarmResponse = alarmService.listAlarmStatus(status, pageable).map(AlarmResponse::from);

		return BaseResponse.success(
			new PageResponse<>(alarmResponse));
	}
}
