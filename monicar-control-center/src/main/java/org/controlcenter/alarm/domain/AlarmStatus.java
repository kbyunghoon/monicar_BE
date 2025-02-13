package org.controlcenter.alarm.domain;

import org.controlcenter.common.exception.BusinessException;
import org.controlcenter.common.response.code.ErrorCode;

public enum AlarmStatus {
	REQUIRED {
		@Override
		public AlarmStatus next() {
			return SCHEDULED;
		}
	},
	SCHEDULED {
		@Override
		public AlarmStatus next() {
			return IN_PROGRESS;
		}
	},
	IN_PROGRESS {
		@Override
		public AlarmStatus next() {
			return COMPLETED;
		}
	},
	COMPLETED {
		@Override
		public AlarmStatus next() {
			throw new BusinessException(ErrorCode.FORBIDDEN_ACCESS);
		}
	};

	public abstract AlarmStatus next();
}
