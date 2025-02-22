package org.controlcenter.notice.application;

import java.util.List;

import java.util.Optional;

import org.controlcenter.notice.domain.Notice;

public interface NoticeRepository {
	Optional<Notice> findById(Long noticeId);
	List<Notice> findAll();
}
