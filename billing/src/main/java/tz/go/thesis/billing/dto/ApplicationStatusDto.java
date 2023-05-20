package tz.go.thesis.billing.dto;

import lombok.Data;


@Data
public class ApplicationStatusDto {	
	private long applicationId; 
	private int applicationStatus;
	private String trxnId;
}
