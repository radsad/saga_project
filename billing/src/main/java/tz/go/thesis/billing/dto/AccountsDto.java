package tz.go.thesis.billing.dto;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountsDto {
	@NotEmpty
	public Long accId;
	
	@NotNull(message = "Meter Number can not be null")
	public String meterNumber;
	@NotNull(message = "Account Number can not be null")
	public String accountNumber;
	@NotNull(message = "Customer name can not be null")
	public String accountName;
	
	public Double initalReadings;
	@NotNull(message = "Application id can not be null")
	public Long applicationId;	
 
}