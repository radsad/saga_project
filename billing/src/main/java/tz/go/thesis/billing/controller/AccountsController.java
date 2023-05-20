package tz.go.thesis.billing.controller;

import javax.validation.Valid;

import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.RollbackException;
import jakarta.transaction.Transactional;
import tz.go.thesis.billing.config.ApiResponse;
import tz.go.thesis.billing.config.ApiResponse.ApiResponseEntity;
import tz.go.thesis.billing.config.ResponseEnum;
import tz.go.thesis.billing.dto.AccountsDto;
import tz.go.thesis.billing.dto.AccsUpdDto;
import tz.go.thesis.billing.entities.Accounts;
import tz.go.thesis.billing.services.AccountsService;




@RestController
@RequestMapping("/accounts")
public class AccountsController {
	
	
	@Autowired
	ApiResponse apiResponse;
	
	@Autowired
	AccountsService accountsService;
	
	private org.slf4j.Logger logger = LoggerFactory.getLogger(AccountsController.class);

	
	@GetMapping("get_account/{accountId}")
	public ApiResponseEntity<?> getAccountDetailsById(@PathVariable(required = true) Long accountId) {
		return apiResponse.getResponse(accountsService.findByAccountById(accountId),
				ResponseEnum.success);
	}
	
	@GetMapping("get_account/all")
	public ApiResponseEntity<?> getAccountDetails() {
		return apiResponse.getResponse(accountsService.getAll(),
				ResponseEnum.success);
	}
	

	@PostMapping("/create")
	@Transactional(rollbackOn = {Exception.class, Error.class, RollbackException.class })
	public ApiResponseEntity <?> createNewAccount(@Valid @RequestBody AccountsDto accountsDto) {

			Accounts newaccount = accountsService.createNewAccount(accountsDto);
			if (newaccount != null) {
				
			return apiResponse.getResponse(null, newaccount, ResponseEnum.success);
			
			}else {
				
			return apiResponse.getResponse(null, "Failed to create account", null,
					ResponseEnum.failed);
			}
	}
	

	@PostMapping("/update_status")
	public ApiResponse.ApiResponseEntity<?> updateAccountStatus(@Valid @RequestBody AccsUpdDto accountsDto) {

		Accounts acc = accountsService.updateStatus(accountsDto);
		if (acc != null) {
			logger.info("ACCOUNT STATUS UPDATED");
			return apiResponse.getResponse(accountsDto, ResponseEnum.success);
		}

		return apiResponse.getResponse(accountsDto, ResponseEnum.failed);

	}

}
