package org.emulator.device.common.response;

import org.common.dto.CommonResponse;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;

@Builder
public record BaseResponse<T>(
	Integer code,
	String message,
	@JsonInclude(JsonInclude.Include.NON_NULL) Long timestamp,
	@JsonInclude(JsonInclude.Include.NON_NULL) T result
) {
	public static BaseResponse<Void> success() {
		return BaseResponse.<Void>builder()
			.code(200)
			.message(SuccessCode.OK.getMessage())
			.build();
	}

	public static <T> BaseResponse<T> success(T data) {
		return BaseResponse.<T>builder()
			.code(200)
			.message(SuccessCode.OK.getMessage())
			.result(data)
			.build();
	}

	public static BaseResponse<Void> fail(CommonResponse response) {
		return BaseResponse.<Void>builder()
			.code(Integer.parseInt(response.rstCd()))
			.message(response.rstMsg())
			.timestamp(System.currentTimeMillis())
			.build();
	}

	public static BaseResponse<Void> fail(ErrorCode code) {
		return BaseResponse.<Void>builder()
			.code(code.getCode())
			.message(code.getMessage())
			.timestamp(System.currentTimeMillis())
			.build();
	}
}
