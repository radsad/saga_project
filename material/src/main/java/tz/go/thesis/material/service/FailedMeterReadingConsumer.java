package tz.go.thesis.material.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tz.go.thesis.material.event.MeterReadingEvent;
import tz.go.thesis.material.event.MeterReadingStatus;


@Service
public class FailedMeterReadingConsumer {
	
private Logger logger = LoggerFactory.getLogger(FailedMeterReadingConsumer.class);
	
	@Autowired
	private MeterService meterService;
    
	@RabbitListener(queues = {"${meterreadingfailure.rabbitmq.queue}"})
    public void receiver(MeterReadingEvent  mrEvent) {
        
		logger.info("RECEIVE COMPANSATION EVENT FROM MR ID : " + mrEvent.getEventId()+" EVENT: "+ mrEvent.getMeterReadingStatus() );
		
        if(MeterReadingStatus.INITIAL_READING_FAILED.equals(mrEvent.getMeterReadingStatus())){
			meterService.failedMeterReadingUpdateStatus(mrEvent);
        }
    }


}
