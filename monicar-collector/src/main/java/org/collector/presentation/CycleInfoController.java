package org.collector.presentation;

import org.collector.application.CycleInfoService;
import org.collector.common.constant.CycleInfoSize;
import org.collector.common.exception.CustomException;
import org.collector.common.response.CommonResponse;
import org.collector.common.response.ResponseCode;
import org.collector.presentation.dto.CycleInfoRequest;
import org.collector.producer.CycleInfoProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/cycle-info")
@RestController
@RequiredArgsConstructor
public class CycleInfoController {

	private final CycleInfoProducer cycleInfoProducer;
	private final CycleInfoService cycleInfoService;

	@PostMapping
	public CommonResponse<Void> cycleInfoSave(final @Valid @RequestBody CycleInfoRequest request) {
		if(request.cList().size() != CycleInfoSize.CYCLE_INFO_MAX_SIZE.getSize()){
			throw new CustomException(ResponseCode.NOT_CYCLE_INFO_SIZE_ERROR);
		}

		Long mdn = cycleInfoService.cycleInfoSave(request);
		cycleInfoProducer.sendMessage(request);
		return CommonResponse.ok(mdn);
	}
}
