package tz.go.thesis.billing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationRequestDto {
    private Long accId;
	private Long applicationId;
	private String meterNumber;
	private String accountNumber;
	private Double initalReadings;
	private Integer connectionStatus;
}
