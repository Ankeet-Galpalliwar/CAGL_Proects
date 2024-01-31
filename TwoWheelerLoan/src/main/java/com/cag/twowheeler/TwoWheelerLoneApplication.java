package com.cag.twowheeler;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cag.twowheeler.config.SshServerConfig;

@SpringBootApplication
@EnableScheduling
public class TwoWheelerLoneApplication  extends SpringBootServletInitializer implements CommandLineRunner {

	@Autowired
	PasswordEncoder encoder;
	
	public static void main(String[] args) {
		SpringApplication.run(TwoWheelerLoneApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
//		  SshServerConfig.configureSshServer();

		String[] path = { "D:\\Twowheeler_Dealer_managment_Documents\\Dealers_Document",
				"D:\\Twowheeler_Dealer_managment_Documents\\Vehicle_Variant_Images" };

		for (String string : path) {
			File folder = new File(string);
			if (!folder.exists()) {
				boolean created = folder.mkdirs();
				if (created) {
					System.out.println("Folder created successfully.");
				} else {
					System.out.println("Failed to create folder.");
				}
			} else {
				System.out.println("Folder already exists.");
			}

		}
		System.out.println("CODE RUN SUCESSFULLY...!");
		System.out.println(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()));
	}

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(TwoWheelerLoneApplication.class);
    }

}
