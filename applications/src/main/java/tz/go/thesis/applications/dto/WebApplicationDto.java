package tz.go.thesis.applications.dto;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import tz.go.thesis.applications.entity.Applications;


@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class WebApplicationDto {
	//	without ID
	@NotEmpty
	private String trxnId;
	@NotNull(message = "Post type can not be null")
	private Integer postType;
	private String nearByMeter;
	private Long inquiryId;
	
	private String name;
	private String plotNumber;
	private String houseNumber;
	private String blockNumber;
	private String categoryId;
	
	private String areaName;
	private String emailAddress;
	private String areaCoordinates;
	private String gender;
	private String postalAddress;
	private String physicalAddress;
	
	@NotEmpty
	private String mobileNumber;
	private String streetLeader;
	private String propertyOwner;
	private Long regionId;	
	private Long districtId;
	private Long wardId;
	private Integer zoneId;
	
	private Long streetId;
	
	private String profilePicture;
 
	private String reserved01;
	private String reserved02;
	private String reserved03;
	
	

	
	public Applications extractApplication(Long institutionId) {
		Applications application = new Applications();

		
		
		application.setServiceType(this.getCategoryId());
		application.setHouseNumber(this.getHouseNumber());
		application.setInstitutionId(institutionId);
		application.setPlotNumber(this.getPlotNumber());
		
	
		return application;
	}


	
	
}
