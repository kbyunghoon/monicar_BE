package org.controlcenter.company.application;

import org.controlcenter.company.application.port.ManagerRepository;
import org.controlcenter.company.domain.ManagerInformation;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Repository
@Service
@RequiredArgsConstructor
public class ManagerService {
	private final ManagerRepository managerRepository;

	public ManagerInformation getProfile(String id) {
		return managerRepository.getUserProfile(id);
	}
}
