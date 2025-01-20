package org.eventhub.common.response;

import org.common.dto.CommonResponse;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;

import java.util.List;

@Builder
public record BaseResponse<T>(
	Boolean isSuccess,
	@JsonInclude(JsonInclude.Include.NON_NULL) Integer errorCode,
	@JsonInclude(JsonInclude.Include.NON_NULL) String errorMessage,
	@JsonInclude(JsonInclude.Include.NON_NULL) Long timestamp,
	@JsonInclude(JsonInclude.Include.NON_NULL) T result
) {
	public static <T> BaseResponse<T> fail(ErrorCode code) {
		return BaseResponse.<T>builder()
			.isSuccess(false)
			.errorCode(code.getCode())
			.errorMessage(code.getMessage())
			.timestamp(System.currentTimeMillis())
			.build();
	}

	public static BaseResponse<CommonResponse> emulatorSuccess(Long mdn) {
		return BaseResponse.<CommonResponse>builder()
			.isSuccess(true)
			.result(new CommonResponse(
				EmulatorResponseCode.SUCCESS.getCode(),
				EmulatorResponseCode.SUCCESS.getMessage(),
				mdn.toString()))
			.build();
	}

	public static BaseResponse<CommonResponse> emulatorFail(EmulatorResponseCode errorCode, Long mdn) {
		return BaseResponse.<CommonResponse>builder()
			.isSuccess(false)
			.result(new CommonResponse(
				errorCode.getCode(),
				errorCode.getMessage(),
				mdn.toString()))
			.build();
	}
}
