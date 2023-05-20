package tz.go.thesis.meter_readings.service;

import org.springframework.stereotype.Service;


import tz.go.thesis.meter_readings.dto.InitialReadingDto;
import tz.go.thesis.meter_readings.entities.MeterReading;
import tz.go.thesis.meter_readings.event.MaterialValidatedEvent;
import tz.go.thesis.meter_readings.event.MeterReadingEvent;


@Service
public interface MeterReadingService {
	
	public MeterReading saveInitialReading(InitialReadingDto initialReadingDto);
	public MeterReading getSingleMeterReading(String accountNumber);
	public MeterReading getmeterReadingByAccountNumber(String accountNumber);
	
	public MeterReadingEvent newCreateInitialReading(MaterialValidatedEvent mtrEvent);
	public MeterReadingEvent failedMeterReading(MaterialValidatedEvent mtrEvent);
	
	
}
