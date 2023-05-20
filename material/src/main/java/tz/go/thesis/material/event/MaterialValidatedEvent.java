package tz.go.thesis.material.event;


import java.util.Date;
import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;
import tz.go.thesis.material.dto.MaterialRequestDto;

@Data
@NoArgsConstructor
public class MaterialValidatedEvent implements EventTransactions {

    private String eventId = UUID.randomUUID().toString();
    private Date eventDate = new Date();
    private MaterialRequestDto materialRequestDto;
    private MaterialStatus materialStatus;
	@Override
	public String getEventId() {
		
		return eventId;
	}
	@Override
	public Date getDate() {
		return eventDate;
	}
	public MaterialValidatedEvent(MaterialRequestDto materialRequestDto, MaterialStatus materialStatus) {
		super();
		this.materialRequestDto = materialRequestDto;
		this.materialStatus = materialStatus;
	}
	


}
