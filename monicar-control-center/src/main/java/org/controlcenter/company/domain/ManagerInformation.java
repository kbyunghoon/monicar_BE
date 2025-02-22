package org.controlcenter.company.domain;

import com.querydsl.core.annotations.QueryProjection;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ManagerInformation {
	private String email;
	private String nickname;
	private String companyName;
	private String departmentName;

	@QueryProjection
	public ManagerInformation(String email, String nickname, String companyName, String departmentName) {
		this.email = email;
		this.nickname = nickname;
		this.companyName = companyName;
		this.departmentName = departmentName;
	}
}
