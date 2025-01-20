package org.eventhub.common.constant;

import org.eventhub.common.exception.BusinessException;
import org.eventhub.common.response.BaseResponse;
import org.eventhub.common.response.ErrorCode;
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
