package tz.go.thesis.billing.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class MeterResponceDto implements Serializable {

	private static final long serialVersionUID = -3308224204187774865L;
	
	private String trxnId;
	
	private String status;
	
	private String statusCode;
	
	private MeterDto data;
	
	private String message;


}