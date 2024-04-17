package com.luizmedeirosn.futs3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

@SpringBootApplication
public class Futs3Application {

	public static void main(String[] args) {
		SpringApplication.run(Futs3Application.class, args);
		System.out.println(Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8().encode("!@Futs3$$$19V4A"));
	}
}
