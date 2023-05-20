package tz.go.thesis.meter_readings.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tz.go.thesis.meter_readings.event.MaterialStatus;
import tz.go.thesis.meter_readings.event.MaterialValidatedEvent;


@Service
public class MeterReadingConsumer {

	private Logger logger = LoggerFactory.getLogger(MeterReadingConsumer.class);

	@Autowired
	private  MeterReadingService meterReadingService;

	@RabbitListener(queues = { "${material.rabbitmq.queue}" })
	public void receiver(MaterialValidatedEvent mtrEvent) {
		 
		logger.info("RECEIVED EVENT ID FROM MATERIAL : " + mtrEvent.getEventId() + "EVENT: " + mtrEvent.getMaterialStatus() );
		
		 if (MaterialStatus.UPDATE_MATERIAL_COMPLETED.equals(mtrEvent.getMaterialStatus())) {
			meterReadingService.newCreateInitialReading(mtrEvent);
		} else {
		    meterReadingService.failedMeterReading(mtrEvent);
		}
	}
}
