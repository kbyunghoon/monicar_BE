package org.eventhub.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	UNSUPPORTED_HEADER(1002, "지원되지 않는 헤더가 입력되었습니다"),
	ILLEGAL_UTILITY_CLASS_ACCESS(1003, "유틸리티 클래스에 대한 잘못된 접근입니다"),
	INTERNAL_SERVER_ERROR(1004, "서버 오류가 발생하였습니다");

	private final int code;
	private final String message;
}

