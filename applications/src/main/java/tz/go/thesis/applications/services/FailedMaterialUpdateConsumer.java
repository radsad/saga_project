package tz.go.thesis.applications.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tz.go.thesis.applications.event.MaterialStatus;
import tz.go.thesis.applications.event.MaterialValidatedEvent;

@Service

public class FailedMaterialUpdateConsumer {
	
  private Logger logger = LoggerFactory.getLogger(FailedMaterialUpdateConsumer.class);
	
	@Autowired
	private UssdService ussdService;
   
	@RabbitListener(queues = { "${materialfailure.rabbitmq.queue}"})
    public void receiver(MaterialValidatedEvent  mtrEvent) {
        logger.info("RECEIVED COMPANSATION EVENT FROM MATERIAL : " + mtrEvent.getEventId()+" EVENT : "+mtrEvent.getMaterialStatus() );
		
        if(MaterialStatus.UPDATE_MATERIAL_FAILED.equals(mtrEvent.getMaterialStatus())){
            
        	ussdService.failedMaterialUpdateStatus(mtrEvent);
        }
    }

}
