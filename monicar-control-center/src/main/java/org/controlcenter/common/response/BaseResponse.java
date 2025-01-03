package org.controlcenter.common.response;

import java.util.List;

import org.controlcenter.common.response.code.ErrorCode;
import org.controlcenter.common.response.code.SuccessCode;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;

/**
 * API 요청에 대한 응답 표준화 클래스
 *
 * @param <T> 요청의 결과 데이터 타입
 */
@Getter
@Builder
public class BaseResponse<T> {
	private final Boolean isSuccess;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final String message;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final List<String> errorMessage;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final T result;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final Integer errorCode;

	public static <T> BaseResponse<T> success(SuccessCode code, T data) {
		return new BaseResponse<>(true, code.getMessage(), null, data, null);
	}

	public static <T> BaseResponse<T> success(T data) {
		return new BaseResponse<>(true, SuccessCode.OK.getMessage(), null, data, null);
	}

	public static <T> BaseResponse<T> success(SuccessCode code) {
		return new BaseResponse<>(true, code.getMessage(), null, null, null);
	}

	public static BaseResponse<Void> success() {
		return success(SuccessCode.OK);
	}

	public static <T> BaseResponse<T> fail(ErrorCode code) {
		return new BaseResponse<>(false, null, List.of(code.getMessage()), null, code.getCode());
	}

	public static <T> BaseResponse<T> fail(List<String> errorMessages) {
		return new BaseResponse<>(false, null, errorMessages, null, 1000);
	}
}
