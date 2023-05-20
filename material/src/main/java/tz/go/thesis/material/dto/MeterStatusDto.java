package tz.go.thesis.material.dto;



import java.util.UUID;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class MeterStatusDto {

	private String trxnId = UUID.randomUUID().toString() ;
	@NotEmpty
	private String meterNumber;
	private String accountNumber;
	private Integer meterConnectionStatus;
	private Integer status;

}