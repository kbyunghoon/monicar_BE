package org.emulator.device.presentation;

import org.emulator.device.application.SseSendWithKeepAliveEmitter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/emulator/sse")
public class GpsSseController {
	private final SseSendWithKeepAliveEmitter sseSend;

	@GetMapping("/stream")
	public SseEmitter streamGpsTransmissionStatus() {
		return sseSend.streamGpsTransmissionStatus();
	}
}
