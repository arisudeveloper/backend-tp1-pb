package br.edu.infnet.sistema_delivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SistemaDeliveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaDeliveryApplication.class, args);
	}

}
