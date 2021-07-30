package com.dao.wethemany;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.dao.wethemany.repository.RoleRepository;

@SpringBootApplication
public class WeTheManAppApplication {

	@Autowired
	RoleRepository roleRepository;
	
	public static void main(String[] args) {
		
		SpringApplication.run(WeTheManAppApplication.class, args);

		
	}

}
