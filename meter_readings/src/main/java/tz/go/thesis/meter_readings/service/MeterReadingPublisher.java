package tz.go.thesis.meter_readings.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import tz.go.thesis.meter_readings.dto.MeterReadingRequestDto;
import tz.go.thesis.meter_readings.event.MeterReadingEvent;
import tz.go.thesis.meter_readings.event.MeterReadingStatus;


@Service
public class MeterReadingPublisher {

	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	
	@Value("${meterreading.rabbitmq.exchange}")
	private String exchange;
	
	@Value("${meterreading.rabbitmq.routingkey}")
	private String routingkey;	
	
	private Logger logger = LoggerFactory.getLogger(MeterReadingPublisher.class);
	
	public void publishMeterReadingSuccessEvent(MeterReadingRequestDto mrRequestDto, MeterReadingStatus mrStatusDto) {
		 MeterReadingEvent mrEvent = new MeterReadingEvent(mrRequestDto, mrStatusDto);
		rabbitTemplate.convertAndSend(exchange, routingkey, mrEvent);
		
		logger.info("SUCCESS MESSAGE SENT EVENT ID: " + mrEvent.getEventId() + " EVENT :" + mrEvent.getMeterReadingStatus());
	    
	}
}
