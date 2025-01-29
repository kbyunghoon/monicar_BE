package org.eventhub.common.exception;

import java.util.List;

import org.eventhub.common.response.BaseResponse;
import org.eventhub.common.response.ErrorCode;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	/**
	 * BusinessException 처리
	 *
	 * @param e 발생한 BusinessException 인스턴스
	 * @return HTTP 상태 코드와 함께 BaseResponse 형식의 오류 응답
	 */
	@ExceptionHandler(BusinessException.class)
	protected BaseResponse handleBusinessException(final BusinessException e) {
		log.error("Business Exception 발생: {}", e.getMessage(), e);
		return BaseResponse.fail(e.getErrorCode());
	}

	/**
	 * 일반적인 모든 예외 처리 (Exception)
	 */
	@ExceptionHandler(Exception.class)
	protected BaseResponse handleGeneralException(final Exception e) {
		log.error("Unhandled Exception 발생: {}", e.getMessage(), e);
		return BaseResponse.fail(ErrorCode.INTERNAL_SERVER_ERROR);
	}

	/**
	 * 타입이 일치하지 않을 때 발생하는 예외 처리
	 * RequestParam 으로 전달된 값의 타입이 맞지 않을 때 주로 발생
	 */
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	protected BaseResponse handleMethodArgumentTypeMismatchException(
		MethodArgumentTypeMismatchException e) {
		log.error("MethodArgumentTypeMismatchException 예외 처리: {}", e.getMessage(), e);
		return BaseResponse.fail(ErrorCode.INVALID_TYPE_VALUE);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public BaseResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
		List<String> errors = fieldErrors.stream()
			.map(fieldError -> String.format("%s : %s", fieldError.getField(), fieldError.getDefaultMessage()))
			.toList();

		log.error("Business Exception 발생: {}", errors.get(0), e);

		return BaseResponse.fail(ErrorCode.REQUIRED_PARAMETER_ERROR);
	}

	/**
	 * 요청에 필수적인 Path Variable이 없을 때 발생하는 예외 처리
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public BaseResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
		log.error("HttpMessageNotReadableException 예외 처리 : {}", e.getMessage(), e);
		return BaseResponse.fail(ErrorCode.EMPTY_PATH_VARIABLE);
	}

	/**
	 * 지원되지 않는 HTTP 메서드로 요청할 때 발생하는 예외 처리
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	protected BaseResponse handleHttpRequestMethodNotSupportedException(
		final HttpRequestMethodNotSupportedException e) {
		log.error("HttpRequestMethodNotSupportedException 예외 처리 : {}", e.getMessage(), e);
		return BaseResponse.fail(ErrorCode.METHOD_NOT_ALLOWED);
	}
}
