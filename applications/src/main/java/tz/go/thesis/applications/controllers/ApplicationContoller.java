package tz.go.thesis.applications.controllers;


import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tz.go.thesis.applications.config.ApiResponse;
import tz.go.thesis.applications.dto.ApplicationStatusUpdateDto;

import tz.go.thesis.applications.repository.ApplicationRepository;
import tz.go.thesis.applications.services.UssdService;


@RestController
@RequestMapping("/applications")
public class ApplicationContoller {

	@Autowired
	private UssdService ussdService;

	@Autowired
	ApiResponse apiResponse;

	@Autowired
	ApplicationRepository appRepo;

	@GetMapping("/{id}")
	public ResponseEntity<?> getApplication(@PathVariable Long id) {
		return ussdService.getSingleApplication(id);
	}
	
	@PutMapping("/update_status")
	public ResponseEntity<?> updateApplicationStatus(@Valid @RequestBody ApplicationStatusUpdateDto data) throws Exception {  
			return ussdService.updateApplicationStatus(data);
	}

	
}
