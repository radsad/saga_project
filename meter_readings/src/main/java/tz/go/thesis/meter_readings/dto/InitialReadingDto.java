package tz.go.thesis.meter_readings.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class InitialReadingDto {

	private String trxnId = UUID.randomUUID().toString();

	private String accountNumber;
	
	private String meterNumber;
	
	private Long applicationId;
	
	private Double currentReadings;
	
	private LocalDateTime deletedAt;
	
	private Integer meterStatus;

}
