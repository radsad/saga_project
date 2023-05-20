package tz.go.thesis.applications.response;


import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WebAppResponse {
private String CN;
private String trackingNumber;
private BigDecimal amount;
public WebAppResponse(String cN, String trackingNumber,BigDecimal amount) {
	super();
	CN = cN;
	this.trackingNumber = trackingNumber;
	this.amount = amount;
}

public WebAppResponse(String trackingNumber) {
	super();
	this.trackingNumber = trackingNumber;
}
public WebAppResponse() {
	super();

}

}