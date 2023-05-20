package tz.go.thesis.billing.dto;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class MeterStatusDto { 
	private String trxnId; 
	@NotEmpty
	private String meterNumber;
 	@NotNull
	private Long institutionId; 
	@NotNull
	private Long userId; 
	private String description; 
	private int status; 
	private String reservedField01;
	private String reservedField02;
	private String reservedField03;   
		 

}