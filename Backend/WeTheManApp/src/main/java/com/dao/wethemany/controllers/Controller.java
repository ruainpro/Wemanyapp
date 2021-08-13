package com.dao.wethemany.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dao.wethemany.response.MessageResponse;

@CrossOrigin(origins = "*", maxAge = 6000)
@RestController
@RequestMapping("/")
public class Controller {
	
	
	@GetMapping()
	public ResponseEntity<?>  gettProduct() {
				
		return ResponseEntity.ok("Application Works ");
	}


}
