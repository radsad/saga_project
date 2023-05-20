package tz.go.thesis.billing.dto;


import java.io.Serializable;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter; 
@Component 
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown=true)
public class ApplicationsDto implements Serializable {
 
	private static final long serialVersionUID = 5445826648932442831L;

	private Long applicationsId; 
    
    private String applicantName;
	private String accountNumber;
	private String mobileNumber;
	private String emailAddress;
 
    private Integer status;
    private String areaName; 
    private int updatedBy;
  
    private BigDecimal connectionCost;

    private BigDecimal connection_cost_paid_amount;

 
    private String applicationNo;  
    private int connectionStatus; 
    private String plotNumber; 
    private long institutionId; 
  
    
    private String trackingNumber;
  
    
    
  
    /** Default constructor. */
    public ApplicationsDto() {
        super();
    }
}
