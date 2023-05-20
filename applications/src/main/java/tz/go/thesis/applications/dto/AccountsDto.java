package tz.go.thesis.applications.dto;


import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Data
public class AccountsDto {
	@NotEmpty
	public Long accId;
	
	public String meterNumber;
	public String accountNumber;
	public String accountName;
	public Double initalReadings;
	public Long applicationId;	
 
}