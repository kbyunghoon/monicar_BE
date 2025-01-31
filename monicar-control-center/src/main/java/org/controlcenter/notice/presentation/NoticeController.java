package org.controlcenter.notice.presentation;

import java.util.List;

import org.controlcenter.common.exception.BusinessException;
import org.controlcenter.common.response.BaseResponse;
import org.controlcenter.common.response.code.ErrorCode;
import org.controlcenter.notice.application.NoticeRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class NoticeController {
	private final NoticeRepository repository;

	@GetMapping("/notice")
	public BaseResponse<List<SimpleNoticeResponse>> getAllNotice() {
		List<SimpleNoticeResponse> notices = repository.findAll()
			.stream()
			.map(SimpleNoticeResponse::from)
			.toList();

		return BaseResponse.success(notices);
	}

	@GetMapping("/notice/{notice-id}")
	public BaseResponse<SimpleNoticeResponse> getNotice(
		@PathVariable("notice-id") Long noticeId
	) {
		SimpleNoticeResponse response = repository.findById(noticeId)
			.map(SimpleNoticeResponse::from)
			.orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));

		return BaseResponse.success(response);
	}
}
