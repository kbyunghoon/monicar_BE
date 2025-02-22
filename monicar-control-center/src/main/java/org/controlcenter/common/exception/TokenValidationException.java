package org.controlcenter.common.exception;

import lombok.Getter;

import org.controlcenter.common.response.code.ErrorCode;

@Getter
public class TokenValidationException extends RuntimeException {
	private final ErrorCode errorCode;

	public TokenValidationException(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}
}
