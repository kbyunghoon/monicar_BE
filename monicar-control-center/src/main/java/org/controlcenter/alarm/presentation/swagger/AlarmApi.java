package org.controlcenter.alarm.presentation.swagger;

import org.controlcenter.alarm.domain.AlarmStatus;
import org.controlcenter.alarm.presentation.dto.AlarmResponse;
import org.controlcenter.common.response.BaseResponse;
import org.controlcenter.common.response.PageResponse;
import org.controlcenter.common.security.CustomUserDetails;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "실시간 알람 API", description = "실시간 알람 API")
public interface AlarmApi {
	@Operation(summary = "SSE 구독", description = "SSE 구독")
	SseEmitter subscribe(@AuthenticationPrincipal CustomUserDetails user);

	@Operation(summary = "알람 승인 API", description = "점검 필요 -> 점검 예정 -> 점검 진행 -> 점검 완료로 순차적으로 상태 변경")
	void next(
		@AuthenticationPrincipal CustomUserDetails user,
		@Valid @PathVariable(name = "alarm-id") Long alarmId
	);

	@Operation(summary = "이벤트 허브에서 알람 요청용(For event-hub)", description = "Event-hub에서 알람 생성 시 요청")
	void send(
		@RequestHeader("X-API-KEY") String apiKey,
		@Valid @PathVariable(name = "alarm-id") Long alarmId
	);

	@Operation(summary = "상태별 알람 조회", description = "상태별 알람 조회")
	BaseResponse<PageResponse<AlarmResponse>> list(
		@RequestParam(required = false) AlarmStatus status,
		@PageableDefault(size = 8) Pageable pageable
	);
}
