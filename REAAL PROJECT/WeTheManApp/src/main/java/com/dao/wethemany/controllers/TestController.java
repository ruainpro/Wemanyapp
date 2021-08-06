package com.dao.wethemany.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dao.wethemany.models.ERole;
import com.dao.wethemany.models.Role;
import com.dao.wethemany.repository.RoleRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
	
	@Autowired
	RoleRepository roleRepository;
	@GetMapping("/all")
	public String allAccess() {
		
//		Role role =new Role();
//		role.setName(ERole.ROLE_ADMIN);
//		roleRepository.save(role);
//	
//		Role role2 =new Role();
//		role2.setName(ERole.ROLE_USER);
//		roleRepository.save(role2);
//		
//		Role rol3 =new Role();
//		rol3.setName(ERole.ROLE_MODERATOR);
//		roleRepository.save(rol3);
		
		
		return "Public Content.";
	}
	
	@GetMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public String userAccess() {
		return "User Content.";
	}

	@GetMapping("/mod")
	@PreAuthorize("hasRole('MODERATOR')")
	public String moderatorAccess() {
		return "Moderator Board.";
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return "Admin Board.";
	}
}
