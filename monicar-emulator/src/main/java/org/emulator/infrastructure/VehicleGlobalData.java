package org.emulator.infrastructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 에뮬레이터 전역 데이터를 관리하는 클래스
 *
 * @field vehicleStartTime 시동 ON 데이터 이력 목록
 * @field vehicleStopTime  시동 OFF 시간 이력 목록
 */
public class VehicleGlobalData {
    private String carNumber;
    private final String terminalId = EmulatorConstant.TERMINAL_ID;
    private final String manufacturerId = EmulatorConstant.MANUFACTURER_ID;
    private final String packetVersion = EmulatorConstant.PACKET_VERSION;
    private final String deviceId = EmulatorConstant.DEVICE_ID;
    private List<VehicleEventData> vehicleStartDataList;
    private List<VehicleEventData> vehicleStopDataList;
    private ControlData controlData;
    private GeoPointData geoPointData;
    private String totalDistance;

    public VehicleEventData addVehicleStartData(VehicleEventData data) {
        if (vehicleStartDataList == null) {
            vehicleStartDataList = new ArrayList<>();
        }
        vehicleStartDataList.add(data);

        return data;
    }

    public VehicleEventData addVehicleStopData(VehicleEventData data) {
        if (vehicleStopDataList == null) {
            vehicleStopDataList = new ArrayList<>();
        }
        vehicleStopDataList.add(data);
        return data;
    }

    public ControlData updateControlData(ControlData data) {
        controlData = ControlData.builder()
                .controlId(data.getControlId())
                .controlCode(data.getControlCode())
                .cycleInfoTransmitTerm(data.getCycleInfoTransmitTerm())
                .build();
        return data;
    }

    public GeoPointData updateGeoPointData(GeoPointData data) {
        geoPointData = GeoPointData.builder()
                .geoPointId(data.getGeoPointId())
                .updateStatus(data.getUpdateStatus())
                .geoGroupId(data.getGeoGroupId())
                .geoAlertType(data.getGeoAlertType())
                .geoRange(data.getGeoRange())
                .latitude(data.getLatitude())
                .longitude(data.getLongitude())
                .geoPointStartTime(data.getGeoPointStartTime())
                .geoPointStopTime(data.getGeoPointStopTime())
                .build();
        return data;
    }

    public Optional<VehicleEventData> getRecentVehicleStartData() {
        if (vehicleStartDataList != null) {
            return Optional.ofNullable(vehicleStartDataList.getLast());
        }
        return Optional.empty();
    }

    public Optional<VehicleEventData> getRecentVehicleStopData() {
        if (vehicleStopDataList != null) {
            return Optional.ofNullable(vehicleStopDataList.getLast());
        }
        return Optional.empty();
    }
}
