package com.archproj.erp_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ErpBackendApplication {

	public static void main(String[] args) {
		System.out.println(">>> STARTING ERP BACKEND APPLICATION <<<");
		SpringApplication.run(ErpBackendApplication.class, args);
	}

}
