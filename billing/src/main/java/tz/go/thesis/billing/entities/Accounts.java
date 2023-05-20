package tz.go.thesis.billing.entities;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import tz.go.thesis.billing.event.AccountStatus;




@Entity
@Table(name = "accounts")
@Where(clause = "deleted_at is NULL")
@NoArgsConstructor
@Data
@ToString
public class Accounts {

	@Id
	@Column(name = "account_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long accountId;

	@Column(name = "application_id")
	private Long applicationId;

	@Column(name = "account_number", unique = true)
	private String accountNumber;

	@Column(name = "meter_number", unique = true)
	private String meterNumber;

	@Column(name = "account_name")
	private String accountName;

	private Integer status;
	
	@Enumerated(EnumType.STRING)
	private AccountStatus accountStatus;
	
	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;
	
	@Column(name = "update_at", nullable = false)
	@UpdateTimestamp
	private LocalDateTime updatedAt;

	@Column(name = "created_at", nullable = false, updatable = false)
	@CreationTimestamp
	private LocalDateTime createdAt;

	public Accounts(@NotNull String accountNumber, @NotNull Long applicationId,String accountName,
			@NotNull String meterNumber,@NotNull int status, AccountStatus accountStatus) {
		this.accountNumber = accountNumber;
		this.applicationId = applicationId;
		this. accountName= accountName;
		this.meterNumber = meterNumber;
		this.status = status;
		this.accountStatus = accountStatus;
		
		
	}

	public static final class AccountState {
		public static final int PENDING = 1;
		public static final int CONNECTED = 2;
		public static final int FAILED = 3;
		
	}
}
