package com.example.gym_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GymAppApplication {

	public static void main(String[] args) {
		System.out.println(" DATABASE URL =========================== "+System.getenv("DATABASE_URL"));
		System.out.println(" Secret =========================== "+System.getenv("JWT_SECRET"));
//		System.out.println(System.getenv(""));
		SpringApplication.run(GymAppApplication.class, args);
	}

}
