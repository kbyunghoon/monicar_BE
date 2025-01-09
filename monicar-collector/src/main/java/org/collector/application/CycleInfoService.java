package org.collector.application;

import java.util.List;

import org.collector.common.exception.CustomException;
import org.collector.common.response.ResponseCode;
import org.collector.domain.CycleInfo;
import org.collector.domain.VehicleInformation;
import org.collector.infrastructure.repository.CycleInfoRepository;
import org.collector.infrastructure.repository.VehicleInformationRepository;
import org.collector.presentation.dto.CycleInfoRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CycleInfoService {
	private final CycleInfoRepository cycleInfoRepository;
	private final VehicleInformationRepository vehicleInformationRepository;

	public long cycleInfoSave(final CycleInfoRequest request) {
		VehicleInformation vehicleInfo = vehicleInformationRepository.findByMdn(request.mdn())
			.orElseThrow(() -> new CustomException(ResponseCode.ENTITY_NOT_FOUND));

		List<CycleInfo> cycleInfos = request.cList().stream()
			.map(cListRequest -> {
				vehicleInfo.updateTotalDistance(cListRequest.sum());
				return CycleInfo.from(cListRequest, vehicleInfo);
			})
			.toList();

		cycleInfoRepository.saveAll(cycleInfos);

		return vehicleInfo.getMdn();
	}
}
