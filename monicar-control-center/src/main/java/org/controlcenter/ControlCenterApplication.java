package org.controlcenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ControlCenterApplication {
	public static void main(String[] args) {
		SpringApplication.run(ControlCenterApplication.class, args);
	}
}
