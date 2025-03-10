package org.emulator.device.common.exception;

import org.emulator.device.common.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(BusinessException.class)
	protected BaseResponse handleBusinessException(final BusinessException e) {
		return BaseResponse.fail(e.getErrorCode());
	}

	@ExceptionHandler(Exception.class)
	protected BaseResponse handleBusinessException(final Exception e) {
		return BaseResponse.fail(e.getMessage());
	}

	@ExceptionHandler(AsyncRequestTimeoutException.class)
	public ResponseEntity<Void> handleSseTimeoutException(AsyncRequestTimeoutException e) {
		// SSE 요청이 타임아웃된 경우, 아무것도 반환하지 않음
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
