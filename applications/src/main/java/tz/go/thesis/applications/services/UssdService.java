package tz.go.thesis.applications.services;





import org.springframework.http.ResponseEntity;

import java.util.List;

import javax.validation.Valid;
import tz.go.thesis.applications.config.ApiResponse.ApiResponseEntity;
import tz.go.thesis.applications.dto.ApplicationStatusUpdateDto;
import tz.go.thesis.applications.entity.Applications;
import tz.go.thesis.applications.event.ApplicationValidatedEvent;
import tz.go.thesis.applications.event.CreateAccountEvent;
import tz.go.thesis.applications.event.MaterialValidatedEvent;





public interface UssdService {

	ResponseEntity<?> getSingleApplication(Long id);
	
	ApiResponseEntity<?> updateApplicationStatus(@Valid ApplicationStatusUpdateDto data);
	Applications findApplicationsById(Long applicationId);
	List<Applications> getAllapplications();
	
	ApplicationValidatedEvent newCreateAccountEvent (CreateAccountEvent accountEvent);
	ApplicationValidatedEvent cancelCreateAcountEvent (CreateAccountEvent accountEvent);
	
	Applications failedMaterialUpdateStatus(MaterialValidatedEvent mtrEvent);
	
	
    


}
	