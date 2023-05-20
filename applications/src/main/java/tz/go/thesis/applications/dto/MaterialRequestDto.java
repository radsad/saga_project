package tz.go.thesis.applications.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MaterialRequestDto {
	
	private Long accId;
	private String meterNumber;
	private Long applicationId;
	private String accountNumber;
	private Integer meterConnectionStatus;
	private Double initalReadings;

}