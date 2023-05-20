package tz.go.thesis.billing.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import tz.go.thesis.billing.dto.AccountsDto;
import tz.go.thesis.billing.event.AccountStatus;
import tz.go.thesis.billing.event.CreateAccountEvent;

@Service
public class AccountStatusProducer {
	
	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	
	@Value("${createaccount.rabbitmq.exchange}")
	private String exchange;
	
	@Value("${createaccount.rabbitmq.routingkey}")
	private String routingkey;	
	
	private Logger logger = LoggerFactory.getLogger(AccountStatusProducer.class);
	
		
	public void publishCreateAccountEvent(AccountsDto createAccountRequestDto, AccountStatus accountStatus) {
		 CreateAccountEvent accountEvent = new CreateAccountEvent(createAccountRequestDto, accountStatus);
		rabbitTemplate.convertAndSend(exchange, routingkey, accountEvent);
		
		logger.info("Message Event sent ID: " + accountEvent.getEventId() + " EVENT NAME :" + accountEvent.getAccountStatus());
	    
	}

}
