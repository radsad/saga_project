package tz.go.thesis.applications.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import javax.validation.Valid;
import tz.go.thesis.applications.config.ApiResponse;
import tz.go.thesis.applications.config.ApiResponse.ApiResponseEntity;
import tz.go.thesis.applications.config.MSG;
import tz.go.thesis.applications.config.ResponseEnum;
import tz.go.thesis.applications.dto.AccountsDto;
import tz.go.thesis.applications.dto.ApplicationRequestDto;
import tz.go.thesis.applications.dto.ApplicationStatusUpdateDto;
import tz.go.thesis.applications.dto.MaterialRequestDto;
import tz.go.thesis.applications.entity.Applications;
import tz.go.thesis.applications.entity.Applications.ApplicationConnectionState;
import tz.go.thesis.applications.event.ApplicationStatus;
import tz.go.thesis.applications.event.ApplicationValidatedEvent;
import tz.go.thesis.applications.event.CreateAccountEvent;
import tz.go.thesis.applications.event.MaterialValidatedEvent;
import tz.go.thesis.applications.repository.ApplicationRepository;




@Service
public class UssdServiceImpl implements UssdService {

	
	@Autowired
	private ApplicationRepository applRepo;

	@Autowired
	private ApiResponse apiResponse;
	
	@Autowired
	FailedCreateAccountPublisher failureCreateAccountPublisher;
	
	@Autowired
	ApplicationValidatedPublisher applValidatePublisher;
	
	private Logger logger = LoggerFactory.getLogger(UssdServiceImpl.class);
	
	
	@Override
	public ResponseEntity<?> getSingleApplication(Long id) {
		Optional<Applications> app = applRepo.findById(id);
		if (app.isPresent()) {
			return apiResponse.getResponse(null,app.get(), ResponseEnum.success);
		}
		return apiResponse.getResponse(null, MSG.ITEM_NOT_FOUND, null, ResponseEnum.failed);
	}

	@Override
	public ApplicationValidatedEvent newCreateAccountEvent(CreateAccountEvent accountEvent) {
		AccountsDto accRequestDto = accountEvent.getAccountRequestDto();
        ApplicationRequestDto applRequestDto = ApplicationRequestDto.builder()
                .accId(accRequestDto.getAccId())
                .applicationId(accRequestDto.getApplicationId())
                .meterNumber(accRequestDto.meterNumber)
                .accountNumber(accRequestDto.accountNumber)
                .initalReadings(accRequestDto.initalReadings)
                .connectionStatus(ApplicationConnectionState.APPLIED)
                .build();

        Applications  appl = findApplicationsById(accRequestDto.getApplicationId());
        
        if (appl != null) {
			appl.setConnectionStatus(ApplicationConnectionState.APPLIED);
			appl.setUpdatedAt(LocalDateTime.now());
			applRepo.save(appl);
			logger.info("UPDATE APP: FINISHED FOR ACCNO: " + accRequestDto.getAccId() + " AND APPNo "+ accRequestDto.getApplicationId());
			applValidatePublisher.publishApplicationValidationEvent(applRequestDto, ApplicationStatus.UPDATE_APPLICATION_COMPLETED);
			return new ApplicationValidatedEvent(applRequestDto, ApplicationStatus.UPDATE_APPLICATION_COMPLETED);
	   }else {
		   
		   return cancelCreateAcountEvent(accountEvent);
		   
	   }
	}
	
	
	
	
	@Override
	public ApplicationValidatedEvent cancelCreateAcountEvent(CreateAccountEvent accountEvent) {
		
		AccountsDto accRequestDto = accountEvent.getAccountRequestDto();
        ApplicationRequestDto applRequestDto = ApplicationRequestDto.builder()
                .accId(accRequestDto.getAccId())
                .applicationId(accRequestDto.getApplicationId())
                .connectionStatus(ApplicationConnectionState.FREE)
                .build();
        
        failureCreateAccountPublisher.publishCreateAccountFailureEvent(applRequestDto, ApplicationStatus.UPDATE_APPLICATION_FAILED);
        return null;
	}

	
	@Override
	public Applications failedMaterialUpdateStatus(MaterialValidatedEvent mtrEvent) {
		
		MaterialRequestDto mtrRequestDto = mtrEvent.getMaterialRequestDto();
		logger.info("COMPANSANTE STATUS " + mtrRequestDto.toString());
		
		 ApplicationRequestDto applRequestDto = ApplicationRequestDto.builder()
	                .accId(mtrRequestDto.getAccId())
	                .applicationId(mtrRequestDto.getApplicationId())
	                .meterNumber(mtrRequestDto.getMeterNumber())
	                .build();
		
		 Applications  appl = findApplicationsById(mtrRequestDto.getApplicationId());
	        
	        if (appl != null) {
				appl.setConnectionStatus(ApplicationConnectionState.FREE);
				appl.setUpdatedAt(LocalDateTime.now());
				applRepo.save(appl);
				logger.info("COMPANSATE APP: FINISHED FOR ACCNO: " + mtrRequestDto.getAccId() + " AND APPNo "+ mtrRequestDto.getApplicationId());
				failureCreateAccountPublisher.publishCreateAccountFailureEvent(applRequestDto, ApplicationStatus.UPDATE_APPLICATION_FAILED);
		    return appl;
	        }else {
			   
	         failureCreateAccountPublisher.publishCreateAccountFailureEvent(applRequestDto, ApplicationStatus.UPDATE_APPLICATION_FAILED);
			   return null;
		   }
	}
	
	
	
	@Override
	public ApiResponseEntity<?> updateApplicationStatus(@Valid ApplicationStatusUpdateDto data) {
		
		logger.info("UPDATE STATUS STARTED " + data.getTrxnId());
		Applications  appl = findApplicationsById(data.getApplicationId());
		if (appl != null) {
			    
				appl.setStatus(data.getApplicationStatus());
				appl.setConnectionStatus(data.getConnectionStatus());
				appl.setUpdatedAt(LocalDateTime.now());
				if (applRepo.save(appl) != null) {
					logger.info("UPDATE STATUS FINIESHED " + data.getTrxnId());
					return apiResponse.getResponse(data.getTrxnId(),"Application Successfully updated!",ResponseEnum.success);
				}
				
				return apiResponse.getResponse(data.getTrxnId(),"Application not saved! ",ResponseEnum.failed);	
		}
		return apiResponse.getResponse(data.getTrxnId(),"application Id not found! ",ResponseEnum.validationFailed);	
	}

	
	@Override
	public Applications findApplicationsById(Long applicationId) {
		return applRepo.findByapplicationId(applicationId);
	}


	@Override
	public List<Applications> getAllapplications() {
		return applRepo.findAll();
	}

	

	

}
