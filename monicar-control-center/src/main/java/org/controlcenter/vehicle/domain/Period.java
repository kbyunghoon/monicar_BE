package org.controlcenter.vehicle.domain;

import java.time.LocalDate;

public enum Period {
	WEEK {
		@Override
		public LocalDate minus(LocalDate date) {
			return date.minusWeeks(1);
		}
	}, MONTH {
		@Override
		public LocalDate minus(LocalDate date) {
			return date.minusMonths(1);
		}
	}, THREE_MONTHS {
		@Override
		public LocalDate minus(LocalDate date) {
			return date.minusMonths(3);
		}
	};

	public abstract LocalDate minus(LocalDate date);
}
