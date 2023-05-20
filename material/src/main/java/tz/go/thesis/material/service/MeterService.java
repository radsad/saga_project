package tz.go.thesis.material.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import tz.go.thesis.material.config.ApiResponse.ApiResponseEntity;
import tz.go.thesis.material.dto.MeterStatusDto;
import tz.go.thesis.material.entity.Meter;
import tz.go.thesis.material.event.ApplicationValidatedEvent;
import tz.go.thesis.material.event.MaterialValidatedEvent;
import tz.go.thesis.material.event.MeterReadingEvent;


@Service
public interface MeterService {

	public Meter findByMeterNumber(String meterNumber);
	public Meter findByMeterId(Long meterId);
	public List<Meter> getAllmeters();
	public ApiResponseEntity<?> updateMeterStatus(@Valid MeterStatusDto meterRequestDto);
	
	MaterialValidatedEvent newUpadateMaterial (ApplicationValidatedEvent appEvent);
	MaterialValidatedEvent FailedUpdateMaterialStatus (ApplicationValidatedEvent appEvent);
	public Meter failedMeterReadingUpdateStatus(MeterReadingEvent mrEvent);
	


}