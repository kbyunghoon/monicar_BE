package org.collector.common.constant;

import lombok.Getter;

@Getter
public enum CycleInfoSize {
	CYCLE_INFO_MAX_SIZE(60),
	CYCLE_INFO_LAST_INDEX(59),;

	private final int size;

	CycleInfoSize(int size) {
		this.size = size;
	}
}
