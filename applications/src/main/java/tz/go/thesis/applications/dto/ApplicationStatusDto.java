package tz.go.thesis.applications.dto;



import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ApplicationStatusDto {
	private String trxnId;
	@NotNull(message = "Application Id can not be null")
	private Long applicationId;
	@NotNull(message = "Application status can not be null")
	private Integer applicationStatus;
}
