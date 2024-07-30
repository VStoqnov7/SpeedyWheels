package com.example.speedywheels;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SpeedyWheelsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpeedyWheelsApplication.class, args);
	}

}
