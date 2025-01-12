package org.controlcenter.notice.presentation;

import java.time.LocalDateTime;

import lombok.Builder;

import org.controlcenter.notice.domain.Notice;

@Builder
public record SimpleNoticeResponse(
	Long id,
	String title,
	String content,
	String imageUrl,
	LocalDateTime createdAt
) {
	public static SimpleNoticeResponse fromDomain(Notice notice) {
		return SimpleNoticeResponse.builder()
			.id(notice.getId())
			.title(notice.getTitle())
			.content(notice.getContent())
			.imageUrl(notice.getImageUrl())
			.createdAt(notice.getCreatedAt())
			.build();
	}
}
