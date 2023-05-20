package tz.go.thesis.billing.dto;


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.annotation.Nullable;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class MeterDto {

	private Long Id;
	private String meterNumber;
	private Date createdAt;
	private String meterType;
	private int digitsLength;
	private Float currentReading;
	private Long institutionId;
	private Integer status;
	private Date updatedAt;
	private Long updatedBy; 
	private String baylanGuid; 
	@Nullable
	private String batchNumber;
 
}