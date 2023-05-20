package tz.go.thesis.material.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import tz.go.thesis.material.config.ApiResponse;
import tz.go.thesis.material.config.ApiResponse.ApiResponseEntity;
import tz.go.thesis.material.config.ResponseEnum;
import tz.go.thesis.material.dto.MeterStatusDto;
import tz.go.thesis.material.entity.Meter;

import tz.go.thesis.material.service.MeterService;


@RestController
@RequestMapping("/material")
public class MeterController {

	@Autowired
	private MeterService meterService;

	@Autowired
	private ApiResponse apiResponse;

	@PutMapping("/update_status")
	public ApiResponseEntity<?> setMeterStatus(@Valid @RequestBody MeterStatusDto statusDto) {
		return meterService.updateMeterStatus(statusDto);
	}

	@GetMapping("/get_meter/{meterNumber}")
	public ApiResponseEntity<?> getMeter(@PathVariable String meterNumber) {
		Meter meters = meterService.findByMeterNumber(meterNumber);
		return apiResponse.getResponse(meters, ResponseEnum.success);
	}

	@GetMapping("/get_meter/all")
	public ApiResponseEntity<?> getAllMeters() {
		
		return apiResponse.getResponse(meterService.getAllmeters(),
				ResponseEnum.success);
	}

	

}