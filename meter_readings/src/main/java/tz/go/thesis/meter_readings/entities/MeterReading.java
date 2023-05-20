package tz.go.thesis.meter_readings.entities;


import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Where(clause = "deleted_at is NULL")
@Table(name = "meter_readings")
@NoArgsConstructor
public class MeterReading {

	@Id
	@GeneratedValue(generator = "readings_id_seq",strategy = GenerationType.IDENTITY)
	@SequenceGenerator(name = "readings_id_seq",initialValue = 1,allocationSize = 1)
	@Column(columnDefinition = "bigint NOT NULL default nextval('readings_id_seq')")
	private Long id;
	
	@CreationTimestamp
	@Column(name="read_date")
	private Date readDate;

	@Column(name = "meter_number")
	private String meterNumber;
	
	@Column(name = "account_number")
	private String accountNumber;

	@Column(name = "current_readings",nullable=false)
	private Double currentReadings;
	
	private Double previousReadings;
	
	private Integer meterStatus;
	
	@Column(name = "usage")
	private Double usage;
	
	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;
	
	@Column(name = "created_at", nullable = false, updatable = false)
	@CreationTimestamp
	private Date createdAt;

	@Column(name = "updated_at", nullable = false)
	@UpdateTimestamp
	private Date updatedAt;

	public MeterReading(Long id, Date readDate, String meterNumber, String accountNumber, Double currentReadings,
			Double previousReadings, Integer meterStatus, Double usage, LocalDateTime deletedAt, Date createdAt,
			Date updatedAt) {
		super();
		this.id = id;
		this.readDate = readDate;
		this.meterNumber = meterNumber;
		this.accountNumber = accountNumber;
		this.currentReadings = currentReadings;
		this.previousReadings = previousReadings;
		this.meterStatus = meterStatus;
		this.usage = usage;
		this.deletedAt = deletedAt;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	
    
	
	
}