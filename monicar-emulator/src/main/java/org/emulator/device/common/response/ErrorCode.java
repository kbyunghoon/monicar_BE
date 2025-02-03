package org.emulator.device.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	/* emulator 관련 */
	INVALID_ACCESS_PATH(100, "Invalid access path."),
	WRONG_APPROACH(101, "This is the wrong approach."),
	MISSING_KEY_VERSION(109, "Missing Key-Version."),
	REQUIRED_PARAMETER_ERROR(301, "Required parameter error."),
	NO_SEARCH_RESULTS(302, "There are no search results."),
	MISMATCHED_MDN(304, "Mismatched MDN."),
	DATA_PROCESSING_ERROR(400, "An error occurred while processing data."),
	UNDEFINED_ERROR(500, "An undefined error has occurred"),

	/* api 관련 */
	UNSUPPORTED_HEADER(1002, "지원하지 않는 헤더입니다"),
	ILLEGAL_UTILITY_CLASS_ACCESS(1003, "유틸리티 클래스에 대한 잘못된 접근입니다"),
	INTERNAL_SERVER_ERROR(1004, "서버 오류가 발생하였습니다"),
	UNSUPPORTED_RESPONSE(1005, "지원하지 않는 응답입니다"),

	NO_GPS_DATA_FOUND(1006, "GPS 데이터 생성이 실패했습니다"),
	FAIL_RESPONSE_DELIVERED(1007, "요청 보낸 서버로부터 온 실패 응답입니다"),
	FILE_LOAD_FILE(1008, "파일 읽기에 실패했습니다");

	private final int code;
	private final String message;
}
