package tz.go.thesis.billing.services;


import java.util.List;

import javax.validation.Valid;

import tz.go.thesis.billing.dto.AccountsDto;
import tz.go.thesis.billing.dto.AccsUpdDto;
import tz.go.thesis.billing.entities.Accounts;
import tz.go.thesis.billing.event.ApplicationValidatedEvent;
import tz.go.thesis.billing.event.MeterReadingEvent;

public interface AccountsService {
	public Accounts createNewAccount(@Valid AccountsDto accountsDto);
	public Accounts  failedUpdateStatus(ApplicationValidatedEvent appEvent);
	public Accounts  successUpdateStatus(MeterReadingEvent appEvent);
	public Accounts updateStatus(AccsUpdDto accountsDto);
	public Accounts findByAccountById(Long id);
	public List<Accounts> getAll();
	
}
