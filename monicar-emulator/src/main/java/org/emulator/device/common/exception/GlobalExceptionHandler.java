package org.emulator.device.common.exception;

import org.emulator.device.common.response.BaseResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(BusinessException.class)
	protected BaseResponse<Void> handleBusinessException(final BusinessException e) {
		return BaseResponse.fail(e.getErrorCode());
	}
}
