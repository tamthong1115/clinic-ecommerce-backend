package com.fg.clinicservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ClinicserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClinicserviceApplication.class, args);
	}

}
