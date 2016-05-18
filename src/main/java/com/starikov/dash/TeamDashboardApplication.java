package com.starikov.dash;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;

@SpringBootApplication
public class TeamDashboardApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(TeamDashboardApplication.class, args);
	}
}
