package tz.go.thesis.applications.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import tz.go.thesis.applications.dto.ApplicationRequestDto;
import tz.go.thesis.applications.event.ApplicationStatus;
import tz.go.thesis.applications.event.ApplicationValidatedEvent;


@Service
public class ApplicationValidatedPublisher {
	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	
	@Value("${application.rabbitmq.exchange}")
	private String exchange;
	
	@Value("${application.rabbitmq.routingkey}")
	private String routingkey;	
	
	 private Logger logger = LoggerFactory.getLogger(ApplicationValidatedPublisher.class);
	
	public void publishApplicationValidationEvent(ApplicationRequestDto applRequestDto, ApplicationStatus appStatusDto) {
		
		ApplicationValidatedEvent appEvent = new ApplicationValidatedEvent(applRequestDto, appStatusDto);
		rabbitTemplate.convertAndSend(exchange, routingkey, appEvent);
		
		logger.info("Message Event sent ID: " + appEvent.getEventId() + " EVENT :" + appEvent.getApplicationStatus());
	}
}
