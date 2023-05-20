package tz.go.thesis.meter_readings.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tz.go.thesis.meter_readings.config.ApiResponse;
import tz.go.thesis.meter_readings.dto.InitialReadingDto;
import tz.go.thesis.meter_readings.dto.MaterialRequestDto;
import tz.go.thesis.meter_readings.dto.MeterReadingRequestDto;
import tz.go.thesis.meter_readings.entities.MeterReading;
import tz.go.thesis.meter_readings.event.MaterialValidatedEvent;
import tz.go.thesis.meter_readings.event.MeterReadingEvent;
import tz.go.thesis.meter_readings.event.MeterReadingStatus;
import tz.go.thesis.meter_readings.repository.MeterReadingRepository;


@Service
public class MeterReadingServiceImpl implements MeterReadingService {

	@Autowired
	private MeterReadingRepository meterReadingRepository;
	
	@Autowired
	FailedMeterReadingPublisher failedMeterReadingPublisher;
	
	@Autowired
	MeterReadingPublisher meterReadingPublisher;
	
	@Autowired
	ApiResponse apiResponse;

	
	private Logger logger = LoggerFactory.getLogger(MeterReadingServiceImpl.class);

	@Override
	public MeterReading saveInitialReading(InitialReadingDto initialReadingDto) {
		
		logger.info(" START INSERTING METER-READINGS: " + initialReadingDto.getTrxnId());
		
		MeterReading mtrRead = getmeterReadingByAccountNumber(initialReadingDto.getAccountNumber());
		if (mtrRead == null) {
			MeterReading meterReading = new MeterReading();
			meterReading.setCurrentReadings(initialReadingDto.getCurrentReadings());
			meterReading.setAccountNumber(initialReadingDto.getAccountNumber());
			meterReading.setMeterNumber(initialReadingDto.getMeterNumber());
			meterReading.setPreviousReadings(initialReadingDto.getCurrentReadings());
			meterReading.setUsage(0.0);
			meterReading.setMeterStatus(initialReadingDto.getMeterStatus());
			
			
			if( meterReadingRepository.save(meterReading).getId() != null) {
				logger.info("METER-READING SAVED  " + initialReadingDto.getTrxnId());
				return meterReading;
			}
			
			return null;
		}
		
		return null;
		
	}
	
	@Override
	public MeterReadingEvent newCreateInitialReading(MaterialValidatedEvent mtrEvent) {
		MaterialRequestDto mtrRequestDto = mtrEvent.getMaterialRequestDto();
	       
		MeterReadingRequestDto mrRequestDto = MeterReadingRequestDto.builder()
                .accId(mtrRequestDto.getAccId())
                .accountNumber(mtrRequestDto.getAccountNumber())
                .applicationId(mtrRequestDto.getApplicationId())
                .meterNumber(mtrRequestDto.getMeterNumber())
                .build();
		MeterReading mtrRead = getmeterReadingByAccountNumber(mtrRequestDto.getAccountNumber());
		if (mtrRead == null) {
		
			MeterReading mtrReading = meterReadingRepository.save(convertDtoToEntity(mtrRequestDto));
			mrRequestDto.setInitReadingId(mtrReading.getId());
			if( mtrReading.getId() != null) {
				logger.info("METER-READING SAVED WITH ID " + mtrReading.getId());
				meterReadingPublisher.publishMeterReadingSuccessEvent(mrRequestDto, MeterReadingStatus.INITIAL_READING_CREATED);
				return new MeterReadingEvent(mrRequestDto , MeterReadingStatus.INITIAL_READING_CREATED);
			}else {
				return failedMeterReading(mtrEvent);
			}
		}else {
			
			return failedMeterReading(mtrEvent);
		}
	}

	private MeterReading convertDtoToEntity(MaterialRequestDto mtrDto) {
		   
		MeterReading meterReading = new MeterReading();
		meterReading.setCurrentReadings(mtrDto.getInitalReadings());
		meterReading.setAccountNumber(mtrDto.getAccountNumber());
		meterReading.setMeterNumber(mtrDto.getMeterNumber());
		meterReading.setPreviousReadings(mtrDto.getInitalReadings());
		meterReading.setUsage(0.0);
		meterReading.setMeterStatus(1);
		return meterReading;
	
}
	
	@Override
	public MeterReadingEvent failedMeterReading(MaterialValidatedEvent mtrEvent) {
		MaterialRequestDto mtrRequestDto = mtrEvent.getMaterialRequestDto();
	       
		MeterReadingRequestDto mrRequestDto = MeterReadingRequestDto.builder()
                .accId(mtrRequestDto.getAccId())
                .accountNumber(mtrRequestDto.getAccountNumber())
                .applicationId(mtrRequestDto.getApplicationId())
                .meterNumber(mtrRequestDto.getMeterNumber())              
                .build();
		failedMeterReadingPublisher.publishMeterReadingFailureEvent(mrRequestDto, MeterReadingStatus.INITIAL_READING_FAILED);
		return new MeterReadingEvent(mrRequestDto , MeterReadingStatus.INITIAL_READING_FAILED);
	}

	

	@Override
	public MeterReading getmeterReadingByAccountNumber(String accountNumber) {
		return  meterReadingRepository.findByAccountNumber(accountNumber);
	}
	
	@Override
	public MeterReading getSingleMeterReading( String accountNumber) {
	     
		return meterReadingRepository.findMeterReadingByAccountNumberAndMeterNumber(accountNumber);
		
	}

	

}
