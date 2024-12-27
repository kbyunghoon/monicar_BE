package org.emulator.infrastructure;

public class VehicleEventData {
    private String carNumber;
    private final String terminalId = EmulatorConstant.TERMINAL_ID;
    private final String manufacturerId = EmulatorConstant.MANUFACTURER_ID;
    private final String packetVersion = EmulatorConstant.PACKET_VERSION;
    private final String deviceId = EmulatorConstant.DEVICE_ID;
    private String vehicleStartTime;
    private String vehicleStopTime;
    private String gpsStatus;
    private String latitude;
    private String longitude;
    private String direction;
    private String speed;
    private String totalDistance;
}
