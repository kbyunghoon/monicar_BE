package org.controlcenter.notice.infrastructure.jpa;

import java.util.Collections;
import java.util.List;

import java.util.Optional;

import lombok.RequiredArgsConstructor;

import org.controlcenter.notice.application.NoticeRepository;
import org.controlcenter.notice.domain.Notice;
import org.controlcenter.notice.infrastructure.jpa.entity.NoticeEntity;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class NoticeRepositoryAdapter implements NoticeRepository {
	private final NoticeJpaRepository jpaRepository;

	@Override
	public List<Notice> findAll() {
		return jpaRepository.findAll()
			.stream()
			.map(NoticeEntity::toDomain)
			.toList();
	}

	@Override
	public Optional<Notice> findById(Long noticeId) {
		return jpaRepository.findByNoticeId(noticeId);
	}
}
