package tz.go.thesis.billing.dto;

import java.util.UUID;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tz.go.thesis.billing.event.AccountStatus;

@NoArgsConstructor
@Getter
@Setter
@Data
public class AccountsResponseDto {
	
	@NotEmpty
	public String trxnId = UUID.randomUUID().toString();
	
	@NotNull
	public String meterNumber;
	@NotNull
	public String accountNumber;
	@NotNull
	public String accountName;
	
	public Double initalReadings;
	@NotNull
	public Long applicationId;
	
	public AccountStatus accountStatus;

}
