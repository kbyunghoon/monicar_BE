package org.controlcenter.notice.presentation.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import org.controlcenter.common.response.BaseResponse;
import org.controlcenter.notice.presentation.SimpleNoticeResponse;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "공지사항 API", description = "공지사항 API")
public interface NoticeApi {
	@Operation(summary = "모든 공지사항 조회", description = "모든 공지사항 조회")
	BaseResponse<List<SimpleNoticeResponse>> getAllNotice();

	@Operation(summary = "특정 공지사항 조회", description = "특정 공지사항 조회")
	BaseResponse<SimpleNoticeResponse> getNotice(
		@Parameter(name = "notice-id", description = "공지사항 ID")
		@PathVariable("notice-id") Long noticeId
	);
}
