package com.issl.grc.ricmodule.exception;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice
public class AppExceptionHandler {

	@ExceptionHandler(value = { SQLException.class })
	public ResponseEntity<Object> sqlExceptionHandler(SQLException ex, WebRequest request) {
		CustomErrorMessage errorMessage = new CustomErrorMessage(new Date(), ex.getMessage());
		return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<Object> otherExceptionHandler(Exception ex, WebRequest request) {
		CustomErrorMessage errorMessage = new CustomErrorMessage(new Date(), ex.getMessage());
		return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.NOT_FOUND);
	}
}
