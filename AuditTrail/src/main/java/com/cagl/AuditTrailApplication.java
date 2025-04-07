package com.cagl;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication

public class AuditTrailApplication extends SpringBootServletInitializer implements CommandLineRunner {
	// Data Source Configuration
	@EnableJpaRepositories(basePackages = "com.cagl.repositorty.datasource1", entityManagerFactoryRef = "entityManagerFactory1", transactionManagerRef = "transactionManager1")
	static class DataSource1Config {
	}

	@EnableJpaRepositories(basePackages = "com.cagl.repositorty.datasource2", entityManagerFactoryRef = "entityManagerFactory2", transactionManagerRef = "transactionManager2")
	static class DataSource2Config {
	}

	public static void main(String[] args) {
		SpringApplication.run(AuditTrailApplication.class, args);

	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(AuditTrailApplication.class);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("CODE \nRUN\n SUCCESSFULLY....!");
	}
}
