package com.example.gym_app;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GymAppApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

		dotenv.entries().forEach(entity ->System.setProperty(entity.getKey(), entity.getValue()));

		System.out.println(" DATABASE URL =========================== "+System.getProperty("DATABASE_URL"));
		System.out.println(" Secret =========================== "+System.getProperty("JWT_SECRET"));
		System.out.println(("Source ============================= "+ System.getProperty("address")));
//		System.out.println(System.getenv(""));
		SpringApplication.run(GymAppApplication.class, args);
	}

}
