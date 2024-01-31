package com.cagl;

//import org.apache.catalina.connector.Response;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
//import okhttp3.RequestBody;
//import okhttp3.MediaType;
//import okhttp3.OkHttpClient;
//import okhttp3.RequestBody;

@SpringBootApplication
public class AuditTrailApplication extends SpringBootServletInitializer implements CommandLineRunner {

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
		// TODO Auto-generated method stub

//		OkHttpClient client = new OkHttpClient().newBuilder()
//				  .build();
//				MediaType mediaType = MediaType.parse("application/json");
//				RequestBody body = RequestBody.create(mediaType, "{\r\n     \"address\": [\r\n          {\r\n               \"addrType\": \"1\",\r\n               \"addrLine1\": \"temple opp madakkana halli road temple\",\r\n               \"city\": \"TUMKUR\",\r\n               \"state\": \"17\",\r\n               \"pinCode\": \"572137\"\r\n          },\r\n          {\r\n               \"addrType\": \"2\",\r\n               \"addrLine1\": \"addrLine1\",\r\n               \"city\": \"TUMKUR\",\r\n               \"state\": \"17\",\r\n               \"pinCode\": \"572137\"\r\n          }\r\n     ],\r\n     \"document\": [\r\n          {\r\n               \"docType\": \"VOTER-ID\",\r\n               \"docId\": \"CKY2226686\"\r\n          }\r\n     ],\r\n     \"depType\": \"F\",\r\n     \"depName\": \"depName\",\r\n     \"categoryId\": \"1\",\r\n     \"productCode\": \"CCR\",\r\n     \"dob\": \"1966-02-05\",\r\n     \"durationOfAgreement\": \"12\",\r\n     \"bankProductId\": \"01\",\r\n     \"custName\": \"Sulochana T\",\r\n     \"gender\": \"2\",\r\n     \"phone\": \"9810866200\",\r\n     \"loanType\": \"2\",\r\n     \"appId\": \"77777777\",\r\n     \"losIndicator\": \"1\",\r\n     \"losIndex\": \"LOS\",\r\n     \"slNo\": \"1\",\r\n     \"loanAmount\": \"8000\",\r\n     \"email\": \"\",\r\n     \"maritalStatus\": \"2\",\r\n     \"kendra\": \"kendra\",\r\n     \"branch\": \"IN0010010\",\r\n     \"stateBranch\": \"5\",\r\n     \"custId\": \"985TRJNGKJE\",\r\n     \"source\": \"OTHERS\",\r\n     \"enquiryType\": \"H\",\r\n     \"loanId\": \"LN00103040\"\r\n}");
//				okhttp3.Request request = new okhttp3.Request.Builder()
//				  .url("http://172.16.101.6:8130/CAGLComboReport/ComboCbReport/makeRequest")
//				  .method("POST", body)
//				  .addHeader("Content-Type", "application/json")
//				  .build();
//				 Response rsponce = client.newCall(request).execute();//client.newCall(request).execute();
//				 
//				 System.err.println(rsponce);
		System.out.println("CODE \nRUN\n SUCCESSFULLY....!");
	}
}
