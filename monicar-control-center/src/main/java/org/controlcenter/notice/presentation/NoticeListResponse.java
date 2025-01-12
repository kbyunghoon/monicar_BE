package org.controlcenter.notice.presentation;

import java.util.List;

import lombok.Builder;

@Builder
public record NoticeListResponse(
	List<SimpleNoticeResponse> noticeList
) {
	public static NoticeListResponse create(List<SimpleNoticeResponse> notices) {
		return NoticeListResponse.builder()
			.noticeList(notices)
			.build();
	}
}
