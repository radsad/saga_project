package tz.go.thesis.material.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import tz.go.thesis.material.dto.MaterialRequestDto;
import tz.go.thesis.material.event.MaterialStatus;
import tz.go.thesis.material.event.MaterialValidatedEvent;


@Service
public class MaterialValidatedPublisher {
	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	
	@Value("${material.rabbitmq.exchange}")
	private String exchange;
	
	@Value("${material.rabbitmq.routingkey}")
	private String routingkey;	
	
	private Logger logger = LoggerFactory.getLogger(MaterialValidatedPublisher.class);
	
	public void publishMaterialValidationEvent(MaterialRequestDto mtrRequestDto, MaterialStatus mtrStatusDto) {
		 MaterialValidatedEvent mtrEvent = new MaterialValidatedEvent(mtrRequestDto, mtrStatusDto);
		rabbitTemplate.convertAndSend(exchange, routingkey, mtrEvent);
		
		logger.info("MESSAGE SENT EVENT ID: " + mtrEvent.getEventId() + " EVENT :" + mtrEvent.getMaterialStatus());
	    
	}
}
