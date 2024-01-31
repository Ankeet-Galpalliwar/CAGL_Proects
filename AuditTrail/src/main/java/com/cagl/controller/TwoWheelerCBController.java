package com.cagl.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author Ankeet G.
 */
@RestController
@CrossOrigin(origins = "*")
public class TwoWheelerCBController {

	@GetMapping("/ComboCbReport")
	public ResponseEntity<Response> ComboCbReport(@RequestParam String dataString) throws IOException {

		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, dataString);
		okhttp3.Request request = new okhttp3.Request.Builder()
				.url("http://172.16.101.6:8130/CAGLComboReport/ComboCbReport/makeRequest").method("POST", body)
				.addHeader("Content-Type", "application/json").build();
		Response rsponce = client.newCall(request).execute();// client.newCall(request).execute();
		return ResponseEntity.status(HttpStatus.OK).body(rsponce);

	}
	
	public ResponseEntity<Response> ComboCbReport(){
		
		
		
		return null;
	}

}
