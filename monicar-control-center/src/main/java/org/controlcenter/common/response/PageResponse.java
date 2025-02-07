package org.controlcenter.common.response;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.Getter;

@Getter
public class PageResponse<T> {
	private final List<T> content;
	private final int page;
	private final int size;
	private final long totalElements;
	private final int totalPages;
	private final boolean first;
	private final boolean last;

	public PageResponse(Page<T> page) {
		this.content = page.getContent();
		this.page = page.getNumber() + 1;
		this.size = page.getSize();
		this.totalElements = page.getTotalElements();
		this.totalPages = page.getTotalPages();
		this.first = page.isFirst();
		this.last = page.isLast();
	}

	public PageResponse(List<T> content, Pageable pageable, long totalElements) {
		this.content = content;
		this.page = pageable.getPageNumber() + 1;
		this.size = pageable.getPageSize();
		this.totalElements = totalElements;
		this.totalPages = (int) Math.ceil((double) totalElements / this.size);
		this.first = this.page == 1;
		this.last = this.page >= this.totalPages;
	}
}