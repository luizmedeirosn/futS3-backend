package com.luizmedeirosn.futs3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.luizmedeirosn.futs3.utils.ClearTerminal;

@SpringBootApplication
public class Futs3Application {

	public static void main(String[] args) {
		ClearTerminal.run();
		SpringApplication.run(Futs3Application.class, args);
	}

}
