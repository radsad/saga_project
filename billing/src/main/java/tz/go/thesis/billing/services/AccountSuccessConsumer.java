package tz.go.thesis.billing.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tz.go.thesis.billing.event.MeterReadingEvent;

@Service
public class AccountSuccessConsumer {
	
	 private Logger logger = LoggerFactory.getLogger(AccountSuccessConsumer.class);
		
	@Autowired
	private AccountsService accountService;
    
	@RabbitListener(queues = {"${meterreading.rabbitmq.queue}"})
    public void receiver(MeterReadingEvent  mrEvent) {
		
		 logger.info("RECEIVED SUCCESS EVENT ID : " + mrEvent.getEventId()+ " EVENT: " + mrEvent.getMeterReadingStatus());
            accountService.successUpdateStatus(mrEvent);
    }
	
}
