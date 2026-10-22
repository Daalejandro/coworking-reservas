package com.bancocuscatlan.mcvs.coworking.reservas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableFeignClients
@SpringBootApplication
public class CoworkingReservasApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoworkingReservasApplication.class, args);
	}

}
