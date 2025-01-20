package org.emulator.device.common.response;

import org.common.dto.CommonResponse;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;

@Builder
public record BaseResponse<T>(
	Boolean isSuccess,
	@JsonInclude(JsonInclude.Include.NON_NULL) Integer errorCode,
	@JsonInclude(JsonInclude.Include.NON_NULL) String errorMessage,
	@JsonInclude(JsonInclude.Include.NON_NULL) Long timestamp,
	@JsonInclude(JsonInclude.Include.NON_NULL) T result
) {
	public static BaseResponse<Void> success() {
		return BaseResponse.<Void>builder()
			.isSuccess(true)
			.build();
	}

	public static <T> BaseResponse<T> success(T data) {
		return BaseResponse.<T>builder()
			.isSuccess(true)
			.result(data)
			.build();
	}

	public static BaseResponse<Void> fail(CommonResponse response) {
		return BaseResponse.<Void>builder()
			.isSuccess(false)
			.errorCode(Integer.parseInt(response.rstCd()))
			.errorMessage(response.rstMsg())
			.timestamp(System.currentTimeMillis())
			.build();
	}

	public static BaseResponse<Void> fail(ErrorCode code) {
		return BaseResponse.<Void>builder()
			.isSuccess(false)
			.errorCode(code.getCode())
			.errorMessage(code.getMessage())
			.timestamp(System.currentTimeMillis())
			.build();
	}
}
