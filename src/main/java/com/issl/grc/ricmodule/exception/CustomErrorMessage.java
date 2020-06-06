package com.issl.grc.ricmodule.exception;

import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class CustomErrorMessage {
	private Date timestamp;
	private String message;

	public CustomErrorMessage() {
		super();
	}

	public CustomErrorMessage(Date timestamp, String message) {
		super();
		this.timestamp = timestamp;
		this.message = message;
	}
}
