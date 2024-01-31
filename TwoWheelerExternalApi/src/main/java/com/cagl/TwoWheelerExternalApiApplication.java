package com.cagl;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class TwoWheelerExternalApiApplication  extends SpringBootServletInitializer implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(TwoWheelerExternalApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		int i = 5;
		while (i > 0) {
			System.out.println("===============\n***************");
			i--;
		}
		System.out.println("External API Applicatation Run Sucessfully......!\n\nCode Run Sucessfully....!");
	}
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(TwoWheelerExternalApiApplication.class);
    }
}
