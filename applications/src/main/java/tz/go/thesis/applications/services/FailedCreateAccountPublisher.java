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
public class FailedCreateAccountPublisher {
	
	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	
	@Value("${application.rabbitmq.exchange}")
	private String exchange;
	
	@Value("${appfailure.rabbitmq.routingkey}")
	private String routingkey;	
	
	private Logger logger = LoggerFactory.getLogger(FailedCreateAccountPublisher.class);
	
	public void publishCreateAccountFailureEvent(ApplicationRequestDto applRequestDto, ApplicationStatus applStatus) {
		 ApplicationValidatedEvent applEvent = new ApplicationValidatedEvent(applRequestDto, applStatus);
		rabbitTemplate.convertAndSend(exchange, routingkey, applEvent);
		
		logger.info("FAILURE MESSAGE SENT EVENT ID: " + applEvent.getEventId() + " EVENT :" + applEvent.getApplicationStatus());
	    
	}
}