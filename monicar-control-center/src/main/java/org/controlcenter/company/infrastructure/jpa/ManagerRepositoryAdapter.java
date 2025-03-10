package org.controlcenter.company.infrastructure.jpa;

import static org.controlcenter.company.infrastructure.jpa.entity.QCompanyEntity.*;
import static org.controlcenter.company.infrastructure.jpa.entity.QDepartmentEntity.*;
import static org.controlcenter.company.infrastructure.jpa.entity.QManagerEntity.*;

import org.controlcenter.company.application.port.ManagerRepository;
import org.controlcenter.company.domain.ManagerInformation;
import org.controlcenter.company.domain.QManagerInformation;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class ManagerRepositoryAdapter implements ManagerRepository {
	private final JPAQueryFactory queryFactory;

	@Override
	public ManagerInformation getUserProfile(String id) {
		return queryFactory
			.select(new QManagerInformation(
				managerEntity.email,
				managerEntity.nickname,
				companyEntity.companyName,
				departmentEntity.departmentName
			))
			.from(managerEntity)
			.join(departmentEntity)
			.on(managerEntity.departmentId.eq(departmentEntity.id))
			.join(companyEntity)
			.on(departmentEntity.companyId.eq(companyEntity.id))
			.where(managerEntity.id.eq(id))
			.fetchOne();
	}
}
