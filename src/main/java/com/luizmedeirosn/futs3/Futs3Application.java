package com.luizmedeirosn.futs3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class Futs3Application {

	public static void main(String[] args) {
		SpringApplication.run(Futs3Application.class, args);
	}
}
