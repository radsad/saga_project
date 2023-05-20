package tz.go.thesis.billing.services;


import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import tz.go.thesis.billing.dto.AccountsDto;
import tz.go.thesis.billing.dto.AccsUpdDto;
import tz.go.thesis.billing.dto.ApplicationRequestDto;
import tz.go.thesis.billing.dto.MeterReadingRequestDto;
import tz.go.thesis.billing.entities.Accounts;
import tz.go.thesis.billing.entities.Accounts.AccountState;
import tz.go.thesis.billing.event.AccountStatus;
import tz.go.thesis.billing.event.ApplicationValidatedEvent;
import tz.go.thesis.billing.event.MeterReadingEvent;
import tz.go.thesis.billing.event.MeterReadingStatus;
import tz.go.thesis.billing.repository.AccountsRepository;

@Service
public class AccountsServiceImpl  implements AccountsService {


	private org.slf4j.Logger logger = LoggerFactory.getLogger(AccountsServiceImpl.class);

	
	
	@Autowired
	AccountsRepository accountsRepository;
	
	@Autowired
	private AccountStatusProducer accountStatusProducer;
	
	@Override
	@Transactional(isolation = Isolation.DEFAULT)
	public Accounts createNewAccount(@Valid AccountsDto accountsDto) {
		
		logger.info("CREATE ACCOUNT INPUT " + accountsDto);

		try {
			Accounts account = accountsRepository.save(convertDtoToEntity(accountsDto));
			accountsDto.setAccId(account.getAccountId());

			if (account.getAccountId() != null) {
				logger.info("ACCOUNT CREATED SUCCESS :" + account.getAccountName());
				accountStatusProducer.publishCreateAccountEvent(accountsDto, AccountStatus.ACCOUNT_CREATED);
				return account;
				
			} else {
				logger.info("ACCOUNT NOT CREATED ");
				return null;
			}

		} catch (DataAccessException e) {
			logger.error("ACCOUNT::FAILED TO SAVE ");
			logger.error(e.getLocalizedMessage());
			return null;
		}
			


	}
	
	private Accounts convertDtoToEntity(AccountsDto accDto) {
		   
		    Accounts newaccount = new Accounts();
			newaccount.setApplicationId(accDto.getApplicationId());
			newaccount.setAccountNumber(accDto.getAccountNumber());
			newaccount.setAccountName(accDto.accountName);
			newaccount.setMeterNumber(accDto.getMeterNumber());
			newaccount.setStatus(AccountState.PENDING);
			newaccount.setAccountStatus(AccountStatus.ACCOUNT_CREATED);
			return newaccount;
		
	}
	
	
	@Override
	public Accounts failedUpdateStatus(ApplicationValidatedEvent appEvent) {
		
		ApplicationRequestDto appRequestDto = appEvent.getApplicationRequestDto();
		logger.info("UPDATING ACCOUNT STATUS " + appRequestDto .toString());
		Accounts acc = accountsRepository.findAccountByAccountId(appRequestDto.getAccId());
		if (acc != null) {
			acc.setStatus(AccountState.FAILED);
			acc.setAccountStatus(AccountStatus.ACCOUNT_CANCELLED);
			if (accountsRepository.save(acc) != null) {
				return acc;
			}
		}
		return null;
	}
	
	
	@Override
	public Accounts successUpdateStatus(MeterReadingEvent mrEvent) {

		MeterReadingRequestDto mrRequestDto = mrEvent.getMeterReadingRequestDto();
		logger.info("ACCOUNT SUCCESS UPDATE: " + mrRequestDto.toString());
		Accounts acc = accountsRepository.findAccountByAccountId(mrRequestDto.getAccId());
		if (acc != null) {
			if (MeterReadingStatus.INITIAL_READING_CREATED.equals(mrEvent.getMeterReadingStatus())) {
				acc.setStatus(AccountState.CONNECTED);
				acc.setAccountStatus(AccountStatus.ACCOUNT_COMPLETED);
			} else {
				acc.setStatus(AccountState.FAILED);
				acc.setAccountStatus(AccountStatus.ACCOUNT_CANCELLED);
			}

			if (accountsRepository.save(acc) != null) {
				return acc;
			}
		}
		return null;
	}
	
	
	@Override
	public Accounts updateStatus(AccsUpdDto accountsDto) {

		logger.info("UPDATING ACCOUNT STATUS " + accountsDto.toString());

		Accounts acc = accountsRepository.findAccountByAccountNo(accountsDto.getAccountNumber());

		if (acc != null) {
			acc.setStatus(accountsDto.getStatus());
			if (accountsRepository.save(acc) != null) {
				return acc;
			}
		}
		return null;
	}
	
	
	@Override
	public Accounts findByAccountById(Long id) {

		Accounts accOpt = accountsRepository.findAccountByAccountId(id);

		return accOpt;
	}

	@Override
	public List<Accounts> getAll() {
		List<Accounts> accs = accountsRepository.findAll();
		return accs;
	}

	

	
}
