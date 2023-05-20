package tz.go.thesis.billing.event;

import java.util.Date;
import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;
import tz.go.thesis.billing.dto.AccountsDto;

@Data
@NoArgsConstructor
public class CreateAccountEvent implements EventTransactions{
	
	private String eventId = UUID.randomUUID().toString();
    private Date eventDate = new Date();
    private AccountsDto accountRequestDto;
    private AccountStatus accountStatus;
    
	@Override
	public String getEventId() {
		
		return eventId;
	}
	
	@Override
	public Date getDate() {
		return eventDate;
	}

	public CreateAccountEvent(AccountsDto accountRequestDto, AccountStatus accountStatus) {
		super();
		this.accountRequestDto = accountRequestDto;
		this.accountStatus = accountStatus;
	}
	
	

}
