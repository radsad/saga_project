package tz.go.thesis.billing.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tz.go.thesis.billing.event.ApplicationValidatedEvent;

@Service
public class FailedAccountStatusConsumer {
  private Logger logger = LoggerFactory.getLogger(FailedAccountStatusConsumer.class);
	
	@Autowired
	private AccountsService accountService;
    
	@RabbitListener(queues = {"${appfailure.rabbitmq.queue}"})
	public void receiver(ApplicationValidatedEvent  applEvent) {
		logger.info("RECEIVED FAILURE EVENT ID : " + applEvent.getEventId()+ " EVENT: " + applEvent.getApplicationStatus());
            accountService.failedUpdateStatus(applEvent);
        
    }
}
