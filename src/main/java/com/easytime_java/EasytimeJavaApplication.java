package com.easytime_java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing; //Importación para la habilitación de la audirotía

@SpringBootApplication
@EnableJpaAuditing //Habilitación de la auditoría
public class EasytimeJavaApplication {
	public static void main(String[] args) {
		SpringApplication.run(EasytimeJavaApplication.class, args);
	}

}
