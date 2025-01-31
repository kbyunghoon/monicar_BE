package org.controlcenter.vehicle.domain;

import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DrivingLogDetailsContent {
	private Long id;
	private LocalDate usageDate;
	private DrivingUserInfo user;
	private DrivingInfo drivingInfo;

	@QueryProjection
	public DrivingLogDetailsContent(Long id, LocalDateTime usageDate, DrivingUserInfo user, DrivingInfo drivingInfo) {
		this.id = id;
		this.usageDate = LocalDate.from(usageDate);
		this.user = user;
		this.drivingInfo = drivingInfo;
	}
}
