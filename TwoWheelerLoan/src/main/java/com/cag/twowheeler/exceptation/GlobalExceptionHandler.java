package com.cag.twowheeler.exceptation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(AlreadyExist.class)
	public ResponseEntity<String> alreadyExist(AlreadyExist msg){
		return ResponseEntity.status(HttpStatus.METHOD_FAILURE).body(msg.getMessage()+"\n"+msg.getErrorCode());
	}
	
	
	@ExceptionHandler(InvalidUser.class)
	public ResponseEntity<String> InvalidUser(InvalidUser msg){
		return ResponseEntity.status(HttpStatus.METHOD_FAILURE).body(msg.getMessage()+"..!");
	}
	
	@ExceptionHandler(CustomExceptation.class)
	public ResponseEntity<String> Exceptation(CustomExceptation msg){
		return ResponseEntity.status(HttpStatus.METHOD_FAILURE).body(msg.getMessage()+"..!");
	}

}
