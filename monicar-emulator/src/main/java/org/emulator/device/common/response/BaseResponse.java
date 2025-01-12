package org.emulator.device.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

import org.common.dto.CommonResponse;

@RequiredArgsConstructor
@Builder
public class BaseResponse<T> {
	private final Integer code;
	private final String message;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final Long timestamp;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final T result;

	public static <T> BaseResponse<T> success() {
		return BaseResponse.<T>builder()
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

	public static <T> BaseResponse<T> fail(CommonResponse response) {
		return BaseResponse.<T>builder()
			.code(Integer.parseInt(response.rstCd()))
			.message(response.rstCd())
			.timestamp(System.currentTimeMillis())
			.build();
	}

	public static <T> BaseResponse<T> fail(ErrorCode code) {
		return BaseResponse.<T>builder()
			.code(code.getCode())
			.message(code.getMessage())
			.timestamp(System.currentTimeMillis())
			.build();
	}
}
