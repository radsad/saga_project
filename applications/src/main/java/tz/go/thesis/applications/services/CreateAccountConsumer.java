package tz.go.thesis.applications.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tz.go.thesis.applications.event.AccountStatus;
import tz.go.thesis.applications.event.CreateAccountEvent;


@Service
public class CreateAccountConsumer {
	
	private Logger logger = LoggerFactory.getLogger(CreateAccountConsumer.class);
	@Autowired
	private UssdService ussdService;
    
	@RabbitListener(queues = {"${createaccount.rabbitmq.queue}"})
    public void receiver(CreateAccountEvent  accountEvent) {
       
	logger.info("Received Event : " + accountEvent.getEventId()  + "Id: " + accountEvent.getAccountStatus() );	
	if(AccountStatus.ACCOUNT_CREATED.equals(accountEvent.getAccountStatus())){
            ussdService.newCreateAccountEvent(accountEvent);
        }else{
            ussdService.cancelCreateAcountEvent(accountEvent);
        }
    }
    
}

