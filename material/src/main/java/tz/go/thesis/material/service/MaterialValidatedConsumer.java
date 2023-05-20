package tz.go.thesis.material.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tz.go.thesis.material.event.ApplicationStatus;
import tz.go.thesis.material.event.ApplicationValidatedEvent;

@Service
public class MaterialValidatedConsumer {
	
	private Logger logger = LoggerFactory.getLogger(MaterialValidatedConsumer.class);

	@Autowired
	private MeterService meterService;

	@RabbitListener(queues = { "${application.rabbitmq.queue}" })
	public void receiver(ApplicationValidatedEvent appEvent) {
		 
		logger.info("RECEIVED EVENT ID FROM APPLICATION : " + appEvent.getEventId());
		logger.info("RECEIVED EVENT : " + appEvent);
		 
		if (ApplicationStatus.UPDATE_APPLICATION_COMPLETED.equals(appEvent.getApplicationStatus())) {
		      meterService.newUpadateMaterial(appEvent);
		} else {
			meterService.FailedUpdateMaterialStatus(appEvent);
		}
	}
}
