package org.collector.domain;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

import org.collector.presentation.dto.GCD;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CycleInfo implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private int sec;
	@Enumerated(EnumType.STRING)
	private GCD gcd;
	private BigDecimal lat;
	private BigDecimal lon;
	private int ang;
	private int spd;
	private int sum;
	private int bat;

	public static BigDecimal convertToSixDecimalPlaces(Double value) {
		return BigDecimal.valueOf(value / 1000000.0);
	}
}
