package org.controlcenter.vehicle.domain;

import com.querydsl.core.annotations.QueryProjection;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DrivingUserInfo {
	private long userId;
	private String departmentName;
	private String name;

	@QueryProjection
	public DrivingUserInfo(Long userId, String departmentName, String name) {
		this.userId = userId;
		this.departmentName = departmentName;
		this.name = name;
	}
}
