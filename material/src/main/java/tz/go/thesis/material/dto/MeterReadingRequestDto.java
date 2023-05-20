package tz.go.thesis.material.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MeterReadingRequestDto {
	private Long  accId;
	private Long  initReadingId;

	private String accountNumber;
	
	private String meterNumber;
	
	private Long applicationId;
	
	private Double currentReadings;
	
	private Integer meterStatus;
}
