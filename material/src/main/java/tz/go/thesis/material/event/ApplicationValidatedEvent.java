package tz.go.thesis.material.event;


import java.util.Date;
import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;
import tz.go.thesis.material.dto.ApplicationRequestDto;


@Data
@NoArgsConstructor
public class ApplicationValidatedEvent implements EventTransactions {

    private String eventId = UUID.randomUUID().toString();
    private Date eventDate = new Date();
    private ApplicationRequestDto applicationRequestDto;
    private ApplicationStatus applicationStatus;
	@Override
	public String getEventId() {
		
		return eventId;
	}
	@Override
	public Date getDate() {
		return eventDate;
	}
	public ApplicationValidatedEvent(ApplicationRequestDto applicationRequestDto, ApplicationStatus applicationStatus) {
		super();
		this.applicationRequestDto = applicationRequestDto;
		this.applicationStatus = applicationStatus;
	}
	
	


}