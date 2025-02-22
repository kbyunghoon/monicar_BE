package org.controlcenter.company.application.port;

import org.controlcenter.company.domain.ManagerInformation;

public interface ManagerRepository {
	ManagerInformation getUserProfile(String id);
}
