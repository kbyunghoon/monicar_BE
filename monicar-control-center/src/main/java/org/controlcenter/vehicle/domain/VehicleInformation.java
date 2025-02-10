package org.controlcenter.vehicle.domain;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class VehicleInformation {
	private Long id;
	private Long companyId;
	private Long vehicleTypeId;
	private String vehicleNumber;
	private Long mdn;
	private String tid;
	private Integer mid;
	private Integer pv;
	private Integer did;
	private Integer drivingDays;
	private Integer sum;
	private VehicleStatus status;
	private LocalDate deliveryDate;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private LocalDateTime deletedAt;

	public static VehicleInformation create(VehicleRegister vehicleRegister) {
		return VehicleInformation.builder()
			.companyId(1L)
			.vehicleTypeId(vehicleRegister.getVehicleTypeId())
			.vehicleNumber(vehicleRegister.getVehicleNumber())
			.mdn(generateMdnGUID())
			.tid("TID001")
			.mid(1)
			.pv(1)
			.did(1)
			.drivingDays(0)
			.sum(vehicleRegister.getDrivingDistance())
			.status(VehicleStatus.NOT_DRIVEN)
			.deliveryDate(vehicleRegister.getDeliveryDate())
			.build();
	}

	public static long generateMdnGUID() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
		String dateStr = sdf.format(new Date());

		Random random = new Random();
		int randomNumber = random.nextInt(1000);
		String randomStr = String.format("%03d", randomNumber);

		return Long.parseLong(dateStr + randomStr);
	}
}