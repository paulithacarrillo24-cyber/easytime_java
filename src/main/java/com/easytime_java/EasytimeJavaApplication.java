package com.easytime_java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing //Habilitar auditorias
public class EasytimeJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(EasytimeJavaApplication.class, args);
	}

}
