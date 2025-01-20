package org.emulator.device.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/emulator")
public class VehicleController {

	/**
	 * 에뮬레이터 home 화면 응답
	 */
	@GetMapping("/home")
	public String keyOn() {
		return "home";
	}
}
