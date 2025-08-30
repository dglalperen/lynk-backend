package com.lynk.lynk_backend;

import com.lynk.lynk_backend.config.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(SecurityConfig.class)
public class LynkBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(LynkBackendApplication.class, args);
	}

}
