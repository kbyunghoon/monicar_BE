package org.emulator.device.common.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;

/**
 * API 요청에 대한 응답 표준화 클래스
 */
@Builder
public record BaseResponse(
	Boolean isSuccess,
	@JsonInclude(JsonInclude.Include.NON_NULL) String message,
	@JsonInclude(JsonInclude.Include.NON_NULL) String errorMessage,
	@JsonInclude(JsonInclude.Include.NON_NULL) Integer errorCode,
	@JsonInclude(JsonInclude.Include.NON_NULL) Long timestamp
) {
	@JsonCreator
	public BaseResponse(
		@JsonProperty("isSuccess") Boolean isSuccess,
		@JsonProperty("message") String message,
		@JsonProperty("errorMessage") String errorMessage,
		@JsonProperty("errorCode") Integer errorCode,
		@JsonProperty("timestamp") Long timestamp
	) {
		this.isSuccess = isSuccess;
		this.message = message;
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
		this.timestamp = timestamp;
	}

	public static BaseResponse success(SuccessCode code) {
		return BaseResponse.builder()
			.isSuccess(true)
			.message(code.getMessage())
			.build();
	}

	public static BaseResponse success() {
		return success(SuccessCode.OK);
	}

	public static BaseResponse fail(String message) {
		return BaseResponse.builder()
			.isSuccess(false)
			.errorMessage(message)
			.timestamp(System.currentTimeMillis())
			.build();
	}

	public static BaseResponse fail(ErrorCode code) {
		return BaseResponse.builder()
			.isSuccess(false)
			.errorMessage(code.getMessage())
			.errorCode(code.getCode())
			.timestamp(System.currentTimeMillis())
			.build();
	}
}
