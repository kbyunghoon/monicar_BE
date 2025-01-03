package org.emulator.device.infrastructure.external;

public enum UrlPathEnum {
	CONTROL_CENTER("control-center"),
	COLLECTOR("collector");

	private final String path;

	UrlPathEnum(String path) {
		this.path = path;
	}

	public String getApiUrl() {
		return "http://localhost:8081/api/v1/" + path + "/";
	}
}
