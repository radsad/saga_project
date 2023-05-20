package tz.go.thesis.billing.dto;

import lombok.Data;


@Data
public class InitialMeterReadingsDto {
	private Long artisanId;
	private String accountNumber;
	private String meterNumber;
	private Long applicationId; 
	private Double currentReadings;
	private Long institutionId;
	private int meterStatus;
	private String trxnId;
	private Long routeId;
	private String remarks; 
	private int readingType;

}