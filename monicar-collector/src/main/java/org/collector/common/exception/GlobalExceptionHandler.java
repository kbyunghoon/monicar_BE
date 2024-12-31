package org.collector.common.exception;

import java.util.Map;
import java.util.stream.Collectors;

import org.collector.common.response.CommonResponse;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler{
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public CommonResponse<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
		Map<String, String> validationErrors = ex.getBindingResult().getFieldErrors().stream()
			.collect(Collectors.toMap(
				FieldError::getField,
				fieldError -> {
					String defaultMessage = fieldError.getDefaultMessage();
					return defaultMessage != null ? defaultMessage : "No message available";
				}
			));

		return CommonResponse.validationError(validationErrors, "111111");
	}
}
