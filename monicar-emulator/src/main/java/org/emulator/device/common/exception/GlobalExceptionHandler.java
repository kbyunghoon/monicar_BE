package org.emulator.device.common.exception;

import org.emulator.device.common.response.BaseResponse;
import org.emulator.device.common.response.ErrorCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(BusinessException.class)
	protected BaseResponse<Void> handleBusinessException(final BusinessException e) {
		return BaseResponse.fail(e.getErrorCode());
	}

	@ExceptionHandler(Exception.class)
	protected BaseResponse<Void> handleBusinessException(final Exception e) {
		return BaseResponse.fail(ErrorCode.INTERNAL_SERVER_ERROR);
	}
}
