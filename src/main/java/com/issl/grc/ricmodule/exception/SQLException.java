package com.issl.grc.ricmodule.exception;

import org.springframework.stereotype.Component;

@Component
public class SQLException extends RuntimeException {

	private static final long serialVersionUID = 703824568294280463L;

	private String message;

	public SQLException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
