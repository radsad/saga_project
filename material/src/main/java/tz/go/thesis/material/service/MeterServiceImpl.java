package tz.go.thesis.material.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tz.go.thesis.material.config.ApiResponse;
import tz.go.thesis.material.config.ApiResponse.ApiResponseEntity;
import tz.go.thesis.material.config.ResponseEnum;
import tz.go.thesis.material.dto.ApplicationRequestDto;
import tz.go.thesis.material.dto.MaterialRequestDto;
import tz.go.thesis.material.dto.MeterReadingRequestDto;
import tz.go.thesis.material.dto.MeterStatusDto;
import tz.go.thesis.material.entity.Meter;
import tz.go.thesis.material.entity.Meter.MeterConnectionState;
import tz.go.thesis.material.event.ApplicationValidatedEvent;
import tz.go.thesis.material.event.MaterialStatus;
import tz.go.thesis.material.event.MaterialValidatedEvent;
import tz.go.thesis.material.event.MeterReadingEvent;
import tz.go.thesis.material.repository.MetersRepository;


@Service
public class MeterServiceImpl implements MeterService {

	@Autowired
	private MetersRepository meterRepository;
	
	@Autowired
	FailedMaterialUpdatePublisher failedMaterialUpdatePublisher;
	
	@Autowired
	MaterialValidatedPublisher materialValidatedPublisher;
	
	@Autowired
	private ApiResponse apiResponse;
	
	private Logger logger = LoggerFactory.getLogger(MeterServiceImpl.class);

	
	@Override
	public Meter findByMeterNumber(String meterNumber) {
		return meterRepository.findByMeterNumber(meterNumber);
	}

	@Override
	public Meter findByMeterId(Long meterId) {
		return meterRepository.findByMeterId(meterId);
	}

	@Override
	public List<Meter> getAllmeters() {
		return meterRepository.findAll();
	}

	@Override
	public ApiResponseEntity<?> updateMeterStatus(@Valid MeterStatusDto statusDto) {
		
		logger.info("METER UPDATE STATUS STARTED " + statusDto.getTrxnId());
		
		Meter  mtr = findByMeterNumber(statusDto.getMeterNumber());
		if (mtr != null) {
			mtr.setStatus(statusDto.getStatus());
			mtr.setMeterConnectionStatus(statusDto.getMeterConnectionStatus());
			mtr.setUpdatedAt(LocalDateTime.now());
				if (meterRepository.save(mtr) != null) {
					logger.info("METER UPDATE STATUS FINIESHED " + statusDto.getTrxnId());
					return apiResponse.getResponse(statusDto.getTrxnId(),"Material Successfully updated!",ResponseEnum.success);
				}
				
				return apiResponse.getResponse(statusDto.getTrxnId(),"Material not saved! ",ResponseEnum.failed);	
		}
		return apiResponse.getResponse(statusDto.getTrxnId(),"Material number not found! ",ResponseEnum.validationFailed);	
	}

	@Override
	public MaterialValidatedEvent newUpadateMaterial(ApplicationValidatedEvent appEvent) {
		ApplicationRequestDto appRequestDto = appEvent.getApplicationRequestDto();
       
		MaterialRequestDto mtrRequestDto = MaterialRequestDto.builder()
                .accId(appRequestDto.getAccId())
                .accountNumber(appRequestDto.getAccountNumber())
                .applicationId(appRequestDto.getApplicationId())
                .meterNumber(appRequestDto.getMeterNumber())
                .initalReadings(appRequestDto.getInitalReadings())
                .build();

		Meter  mtr = findByMeterNumber(appRequestDto.getMeterNumber());
        
        if (mtr != null) {
			mtr.setMeterConnectionStatus(MeterConnectionState.TAKEN);
			mtr.setUpdatedAt(LocalDateTime.now());
			meterRepository.save(mtr);	
			logger.info("UPDATE MATERIAL: FINISHED FOR ACCNO: " + appRequestDto.getAccountNumber() + " AND METERNO "+ appRequestDto.getMeterNumber());
			materialValidatedPublisher.publishMaterialValidationEvent(mtrRequestDto , MaterialStatus.UPDATE_MATERIAL_COMPLETED);
			return new MaterialValidatedEvent(mtrRequestDto , MaterialStatus.UPDATE_MATERIAL_COMPLETED);
	   }else {
		   
		   return FailedUpdateMaterialStatus(appEvent);
		   
	   }
	}

	@Override
	public MaterialValidatedEvent FailedUpdateMaterialStatus(ApplicationValidatedEvent appEvent) {
		
		ApplicationRequestDto appRequestDto = appEvent.getApplicationRequestDto();
		MaterialRequestDto mtrRequestDto = MaterialRequestDto.builder()
                .accId(appRequestDto.getAccId())
                .applicationId(appRequestDto.getApplicationId())
                .meterNumber(appRequestDto.getMeterNumber())
                .build();
        
		failedMaterialUpdatePublisher.publishMaterialFailureEvent(mtrRequestDto , MaterialStatus.UPDATE_MATERIAL_FAILED);
        return new MaterialValidatedEvent(mtrRequestDto , MaterialStatus.UPDATE_MATERIAL_FAILED);
	}
	
	@Override
	public Meter failedMeterReadingUpdateStatus(MeterReadingEvent mrEvent) {
		
		MeterReadingRequestDto mrRequestDto = mrEvent.getMeterReadingRequestDto();
		logger.info("COMPANSANTE STATUS " + mrRequestDto.toString());
		
		 MaterialRequestDto mtrRequestDto = MaterialRequestDto.builder()
	                .accId(mrRequestDto.getAccId())
	                .applicationId(mrRequestDto.getApplicationId())
	                .meterNumber(mrRequestDto.getMeterNumber())
	                .build();
		
		 Meter  mtr = findByMeterNumber(mrRequestDto.getMeterNumber());
	        
	        if (mtr != null) {
				mtr.setMeterConnectionStatus(MeterConnectionState.FREE);
				mtr.setUpdatedAt(LocalDateTime.now());
				meterRepository.save(mtr);	
				logger.info("COMPANSATE MATERIAL: FINISHED FOR ACCNO: " + mrRequestDto.getAccId() + " AND APPNo "+ mrRequestDto.getMeterNumber());
				failedMaterialUpdatePublisher.publishMaterialFailureEvent(mtrRequestDto, MaterialStatus.UPDATE_MATERIAL_FAILED);
		    return mtr;
		    
	        }else {
	        	failedMaterialUpdatePublisher.publishMaterialFailureEvent(mtrRequestDto, MaterialStatus.UPDATE_MATERIAL_FAILED);
			   return null;
		   }
	}
	

	

}