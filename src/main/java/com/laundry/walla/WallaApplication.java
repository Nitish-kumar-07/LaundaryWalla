package com.laundry.walla;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WallaApplication {

	public static void main(String[] args) {
		SpringApplication.run(WallaApplication.class, args);
		System.out.println("🚀 Laundry Walla Backend Started!");
		System.out.println("📍 API: http://localhost:8080/api");
		System.out.println("📁 Frontend: http://localhost:8080");
	}

}
