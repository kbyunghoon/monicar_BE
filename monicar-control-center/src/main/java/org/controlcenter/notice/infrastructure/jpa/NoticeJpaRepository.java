package org.controlcenter.notice.infrastructure.jpa;

import java.util.Optional;

import org.controlcenter.notice.domain.Notice;
import org.controlcenter.notice.infrastructure.jpa.entity.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeJpaRepository extends JpaRepository<NoticeEntity, Long> {
	Optional<Notice> findByNoticeId(Long noticeId);
}
