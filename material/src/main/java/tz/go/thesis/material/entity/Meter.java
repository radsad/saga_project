package tz.go.thesis.material.entity;


import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Table(name="meters")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
public class Meter{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="meter_number")
	private String meterNumber;

	@CreationTimestamp
	@Column(name="created_at")
	private Date createdAt;

	@Column(name="digits_length")
	private Integer digitsLength;
	
	@Column(name="current_reading")
	private Double currentReading;

	@Column(name="institution_id")
	private Long institutionId;

	@Column(name="status")
	private Integer status;
	
	@Column(name="meter_connection_status")
	private Integer meterConnectionStatus;

	@Column(name="updated_at")
	private LocalDateTime updatedAt;
	
	@Column(name="updated_by")
	private Long updatedBy;
	
	public Meter() {
		super();
	}

	public Meter(Long id, String meterNumber, Date createdAt, Integer digitsLength, Double currentReading,
			Long institutionId, Integer status, Integer meterConnectionStatus, LocalDateTime updatedAt,
			Long updatedBy) {
		super();
		this.id = id;
		this.meterNumber = meterNumber;
		this.createdAt = createdAt;
		this.digitsLength = digitsLength;
		this.currentReading = currentReading;
		this.institutionId = institutionId;
		this.status = status;
		this.meterConnectionStatus = meterConnectionStatus;
		this.updatedAt = updatedAt;
		this.updatedBy = updatedBy;
	}


	public static final class MeterConnectionState {
		public static final int TAKEN = 1;
		public static final int FREE = 2;
	}

	


	
	
}