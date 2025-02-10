package org.collector.domain;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Company implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "company_id")
	private Long id;
	private String companyName;
	private String businessRegistrationNumber;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private LocalDateTime deletedAt;
}
