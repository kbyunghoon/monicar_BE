package org.controlcenter.vehicle.domain;

import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.controlcenter.vehicle.presentation.dto.DrivingUserInfo;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DrivingLogDetailsContent {
	private LocalDate usageDate;
	private DrivingUserInfo user;
	private DrivingInfo drivingInfo;

	@QueryProjection
	public DrivingLogDetailsContent(LocalDateTime usageDate, DrivingUserInfo user, DrivingInfo drivingInfo) {
		this.usageDate = LocalDate.from(usageDate);
		this.user = user;
		this.drivingInfo = drivingInfo;
	}
}
