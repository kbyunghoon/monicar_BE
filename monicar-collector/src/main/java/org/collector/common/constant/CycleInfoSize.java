package org.collector.common.constant;

import lombok.Getter;

@Getter
public enum CycleInfoSize {
	MIN_SIZE(60),
	LAST_INDEX(59);

	private final int size;

	CycleInfoSize(int size) {
		this.size = size;
	}
}
