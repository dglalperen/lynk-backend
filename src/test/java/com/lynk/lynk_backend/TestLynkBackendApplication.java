package com.lynk.lynk_backend;

import org.springframework.boot.SpringApplication;

public class TestLynkBackendApplication {

	public static void main(String[] args) {
		SpringApplication.from(LynkBackendApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
