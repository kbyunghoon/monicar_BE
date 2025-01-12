package org.controlcenter.notice.presentation;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.controlcenter.common.exception.BusinessException;
import org.controlcenter.common.response.BaseResponse;
import org.controlcenter.common.response.code.ErrorCode;
import org.controlcenter.notice.infrastructure.jpa.NoticeRepositoryAdapter;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/control-center")
public class NoticeController {
	private final NoticeRepositoryAdapter repository;

	@GetMapping("/notices")
	public BaseResponse<NoticeListResponse> getAllNotice() {
		List<SimpleNoticeResponse> notices = repository.findAll()
			.stream()
			.map(SimpleNoticeResponse::fromDomain)
			.toList();

		return BaseResponse.success(NoticeListResponse.create(notices));
	}

	@GetMapping("/notices/{notice-id}")
	public BaseResponse<SimpleNoticeResponse> getNotice(
		@PathVariable("notice-id") Long noticeId
	) {
		SimpleNoticeResponse response = repository.findById(noticeId)
			.map(SimpleNoticeResponse::fromDomain)
			.orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));

		return BaseResponse.success(response);
	}
}
