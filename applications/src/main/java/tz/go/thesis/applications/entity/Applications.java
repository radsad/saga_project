package tz.go.thesis.applications.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;




import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;




@Entity
@Data
public class Applications {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long applicationsId;
	
	private String applicantName;
	private String accountNumber;
	private String mobileNumber;
	private String emailAddress;
	@Column(name = "plot_number", nullable = true, length = 2044)
	private String plotNumber;
	@Column(name = "house_number", nullable = true, length = 2044)
	private String houseNumber;
	private Integer status;
	
	private BigDecimal connectionCost;
	private BigDecimal connection_cost_paid_amount;
	@Column(name = "connection_status", nullable = false, precision = 10)
	private Integer connectionStatus;

	private LocalDateTime deletedAt;
	@CreationTimestamp
	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_by")
	private Integer updatedBy;

	@UpdateTimestamp
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt;

	
	
	
	@Column(name = "institution_id", nullable = false, precision = 10)
	private long institutionId;
	
	private String trackingNumber;

	private String serviceType;
	
	@Column(name = "connection_types_id")
	private String connection_types_id;


	/** Default constructor. */
	public Applications() {
		super();
	}
	
	
	public static final class ApplicationConnectionState {
		public static final int APPLIED = 1;
		public static final int FREE = 2;
	}

	
}
