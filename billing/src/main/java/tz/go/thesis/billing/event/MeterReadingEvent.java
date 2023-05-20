package tz.go.thesis.billing.event;


import java.util.Date;
import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;
import tz.go.thesis.billing.dto.MeterReadingRequestDto;


@Data
@NoArgsConstructor
public class MeterReadingEvent implements EventTransactions{

    private String eventId = UUID.randomUUID().toString();
    private Date eventDate = new Date();
    private MeterReadingRequestDto meterReadingRequestDto;
    private MeterReadingStatus meterReadingStatus;
	
    @Override
	public String getEventId() {
		
		return eventId;
	}
    
	@Override
	public Date getDate() {
		return eventDate;
	}
	
	public MeterReadingEvent(MeterReadingRequestDto meterReadingRequestDto, MeterReadingStatus meterReadingStatus) {
		super();
		this.meterReadingRequestDto = meterReadingRequestDto;
		this.meterReadingStatus = meterReadingStatus;
	}


}
