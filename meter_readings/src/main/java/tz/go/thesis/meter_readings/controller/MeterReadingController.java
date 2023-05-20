package tz.go.thesis.meter_readings.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tz.go.thesis.meter_readings.config.ApiResponse;
import tz.go.thesis.meter_readings.config.ApiResponse.ApiResponseEntity;
import tz.go.thesis.meter_readings.config.ResponseEnum;
import tz.go.thesis.meter_readings.dto.InitialReadingDto;
import tz.go.thesis.meter_readings.entities.MeterReading;
import tz.go.thesis.meter_readings.service.MeterReadingService;


@RestController
@RequestMapping("/meter-reading")
public class MeterReadingController {

	@Autowired
	private MeterReadingService meterReadingService;

	@Autowired
	private ApiResponse apiResponse;

	Logger logger = (Logger) LoggerFactory.getLogger(MeterReadingController.class);
	
	
	
	@GetMapping("/{accountNumber}")
	public ResponseEntity<?> getMeterReading(@PathVariable  String accountNumber) {
		MeterReading mtrReading = meterReadingService.getSingleMeterReading( accountNumber);
		return apiResponse.getResponse(mtrReading, ResponseEnum.success);
		
	}
	
	@PostMapping("/submit/initial-readings")
	public ApiResponseEntity<?> submitInitalMeterReading(@RequestBody InitialReadingDto initialReadingDto) {

		try {
			 meterReadingService.saveInitialReading(initialReadingDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return apiResponse.getResponse(initialReadingDto.getTrxnId(), null, ResponseEnum.success);

	}

	
}
