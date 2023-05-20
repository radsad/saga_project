package tz.go.thesis.applications.dto;



import java.util.UUID;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ApplicationStatusUpdateDto {

	
	private String trxnId = UUID.randomUUID().toString();
	
	@NotNull
	private Long applicationId;
	
	@NotNull
	private Integer applicationStatus;
	
	private Integer connectionStatus;

	
}
